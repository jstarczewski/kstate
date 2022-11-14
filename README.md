# Welcome to StackEdit!

Project `kmm-mvvm-state` aims to share yet another way to manage state in Kotlin Multiplatform
applications. Pattern tries to provide similar API both for Android and iOS application and as an
example wires state management with MVVM architectural pattern to show more "real-life" use cases.

# Example

Investigate the example below. Important things to take into notice.

1. `ExampleViewModel` is shared both on Android and iOS.
2. Current state is represented via `state` field. Both platforms reflect changes that are made to `state` value.
3. Explicit synchronization is needed when state is updated from threads other than main.
4. Interface of this ViewModel does not contain suspend functions. It can be easily called from Android and iOS.
5. Coroutines launched within this ViewModel's scope will be cancelled automatically both on Android and iOS.

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
