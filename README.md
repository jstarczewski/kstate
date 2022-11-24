# Work in Progress
Project is under development.

# Overview!
Provide observable state to your Jetpack Compose and SwiftUI modules from Kotlin Multiplatfrom Shared module with near zero boilerplate.

## Declare your state in Shared
Use simple `state` property delegate which will wrap the provided value properly to allow observation on platforms.
```Kotlin
import com.jstarczewski.kstate.state
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SimpleViewModel : KmmViewModel() {

    var message by state("Hello World")
        private set

    var isLoading by state(false)
        private set

    fun updateMessage() = viewModelScope.launch {
        isLoading = true
        delay(500)
        message = "Hello kstate"
        isLoading = false
    }
}
```
## Observe state on platforms
Create `SimpleViewModel` in any way an receive state updates.
```kotlin
@Composable
fun SimpleScreen() {

    val viewModel: SimpleViewModel = viewModel { SimpleViewModel() }

    Box(modifier = Modifier.fillMaxSize()) {
        Greeting(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { viewModel.updateMessage() },
            text = viewModel.message
        )
    }
}
```
Use a proper property wrapper provided by library to wire state observation on iOS.
```Swift
struct SimpleView: View {
    
    @ObservedStateHolder var viewModel = SimpleViewModel()
    
	var body: some View {
        Text(viewModel.message)
            .onTapGesture {
                viewModel.updateMessage()
            }
	}
}
```

