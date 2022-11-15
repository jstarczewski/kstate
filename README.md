# Overview!

Project `kmm-mvvm-state` aims to share yet another way to manage state in Kotlin Multiplatform
applications. Pattern tries to provide similar API both for Android and iOS application and as an
example wires state management with MVVM architectural pattern to show more "real-life" use cases.
Please the into account that this solution works fine for apps using Jetpack Compose and SwiftUI to 
draw the UI on platforms, also we are relaying here on new KMM memory management to
utilise multiplatform Kotlin coroutines. 

# Example

Investigate the example below. Some basic requirements that were taken into account regarding the ViewModel approach.

1. `ExampleViewModel` is shared both on Android and iOS.
2. Coroutines launched within this ViewModel's scope will be cancelled automatically both on Android
   and iOS.
3. Interface of this ViewModel does not contain suspend functions. It can be easily called from
      Android and iOS.
4. Asynchronous behaviour can be easily tested in shared module tests, because functions launching
      coroutines are exposing it's `Job` objects.

Let's focus on a way the state is "distributed". 

1. Current state which is `ExampleViewModelState` data class is represented via `state` field. Both platforms reflect changes that are made
   to `state` value.
2. Explicit synchronization is needed when state is updated from threads other than main.
3. Value of `state` is saved and retained in case of process death on Android platform (which is another useful functionality).

```Kotlin
@Parcelize
data class ExampleViewModelState(
    val isLoading: Boolean = false,
    val counter: Int = 0,
    val title: String = "Title",
    val items: List<Int> = emptyList(),
    @IgnoreOnParcel
    val otherCounter: Int = 0
) : Parcelable

class ExampleViewModel(config: Config) : ViewModel(config) {

    var state: ExampleViewModelState by state(ExampleViewModelState())
        private set

    private val mutex = Mutex()

    fun updateState() = viewModelScope.launch(Dispatchers.Default) {
        coroutineScope {
            while (isActive) {
                delay(300)
                repeat(30) {
                    launch {
                        mutex.withLock {
                            state = state.copy(counter = state.counter + 1)
                        }
                    }
                }
            }
        }
    }
}
```

ViewModel's parameter `config` is just a way to provide some platform dependent information's like `savedStateHandle`,
some `key-value` params etc. Let's consider an implementation detail for now.

`ExampleViewModel` has the following interface on iOS platform.

```Swift
struct ExampleView: View {
    
    @ObservedViewModel var viewModel = ExampleViewModel(config: MvvmConfig())
    
	var body: some View {
        Text("Counter = \(viewModel.state.counter)")
            .onTapGesture {
                viewModel.updateState()
            }
	}
}
```

The key here is the `ObservedViewModel` property wrapper which wires-up our common code with platform "reactivity" and
provides other enhancements to make usage of common shared code easier. As presented, we can access the properties of state value 
directly via getter and trigger it's updates in asynchronous manner via non suspending API of ViewModel expecting updates to be reflected 
within the UI. The details of `ObservedViewModel` and other wrappers will be discussed later in this document. I highly encourage to check the source code 
of `ios` app within the project rather than reading this document all the way down. 

On Android, `ExampleViewModel`'s interface is as follows.

```Kotlin
setContent {

    val viewModel: ExampleViewModel = viewModel {
        ExampleViewModel(config = Config(createSavedStateHandle()))
    }

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Greeting(
                    modifier = Modifier
                        .clickable { viewModel.updateState() }
                        .align(Alignment.CenterHorizontally),
                    text = viewModel.state.counter.toString()
                )
            }
        }
    }
}
```

On Android API of ViewModel is the same as on iOS, the ViewModel is created via utility functions just to properly attach it
to lifecycle of chosen components. Platform's `Config` implementation is expecting `SavedStateHandle`, because that's the way the 
state is persisted on process death, but that's also an implementation detail.

# Implementing the state management

The purpose of this project is to demonstrate another way to wire state management tricks on KMM apps, so let's dig first into this and then other components.
I highly encourage to read the source code of `state` module, because that's where the "core" of the approach is (more "vm oriented" elements are in `mvvm` module).

## StateHolder

The basic element of state update hack is `StateHolder` interface. Which should be implemented by "base state holder", typically an abstract ViewModel, some kind
of abstract Store, or a abstract domain object if you are this [MV person](https://developer.apple.com/forums/thread/699003). 

The expect common implementation of `StateHolder` goes as follows
```Kotlin
expect interface StateHolder
```
On Android:
```Kotlin
actual interface StateHolder
```
And finally on iOS some things change. 
```Kotlin
actual interface StateHolder {

    var objectWillChange: () -> Unit
}
```
The `objectWillChange` variable is a callback that is needed to be set to receive updates. I wanted to somehow make it non mutable etc. but encountered some issues 
regarding initialization in Swift, so for now it is a `var` and the "binding" of proper update is wired up automatically via helper Swift classes.

## State delegate

Other important class is the sate property wrapper which in fact wraps our state in common code.
```kotlin
expect class State<T : Any> {

   operator fun getValue(thisRef: Any?, property: KProperty<*>): T

   operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}
```

The implementation on Android goes as follows:
```kotlin
actual class State<T : Any>(
    initialValue: T,
    private val saveValue: (value: T) -> Unit = { }
) {

    var value by mutableStateOf(initialValue)

    actual operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    actual operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        saveValue(value)
        this.value = value
    }
}
```
Here the value of initial state is kept withing `MutableState` of Compose Runtime, which makes it easily observable 
in Compose world. The `saveValue` value probably should not be here, but I was experimenting recently. Though it is triggered with every write
to the value so some "external saver" (... `SavedStateHandle`) can persist the value. 

On `iOS` the implementation goes as follows:

```kotlin
actual class State<T : Any>(
    private var initialValue: T
) {

    private var objectWillChange: () -> Unit = {}

    constructor(
        initialValue: T,
        objectWillChange: () -> Unit
    ) : this(initialValue) {
        this.objectWillChange = objectWillChange
    }

    actual operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return initialValue
    }

    actual operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        initialValue = value
        objectWillChange()
    }
}
```
After every successful set, the `objectWillChange` lambda is triggered, to notify platform that the 
new state value is available. The call to `objectWillChange` can probably be limited scenarios when the new 
`value` differs from previous one. 

# Wiring extensions in shared module

To wire everything there is an extension function defined in common module
```kotlin
expect fun <T : Any> StateHolder.state(
    initialValue: T
): State<T>
```
With following implementation on iOS
```Kotlin
actual fun <T : Any> StateHolder.state(initialValue: T): State<T> =
    State(initialValue) { objectWillChange() }
```
And Android 
```kotlin
actual fun <T : Any> StateHolder.state(initialValue: T): State<T> =
    State(initialValue)
```