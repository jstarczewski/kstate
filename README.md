# Welcome to StackEdit!

Project `kmm-mvvm-state` aims to share yet another way to manage state in Kotlin Multiplatform
applications. Pattern tries to provide similar API both for Android and iOS application and as an
example wires state management with MVVM architectural pattern to show more "real-life" use cases.

# Example

Investigate the example below. Important things to take into notice.

1. `ExampleViewModel` is shared both on Android and iOS.
2. Current state is represented via `state` field. Both platforms reflect changes that are made
   to `state` value.
3. Explicit synchronization is needed when state is updated from threads other than main.
4. Value of `state` is saved and retained in case of process death on Android platform.
5. Interface of this ViewModel does not contain suspend functions. It can be easily called from
   Android and iOS.
6. Coroutines launched within this ViewModel's scope will be cancelled automatically both on Android
   and iOS.
7. Asynchronous behaviour can be easily tested in shared module tests, because functions launching
   coroutines are exposing it's `Job` objects.

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

ViewModel above has the following interface on iOS platform.

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

On Android, the interfaces is as follows.

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
