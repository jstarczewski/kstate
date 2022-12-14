## Work in Progress
Project is under development. Currently only a local version used within a sample KMM app is available as a way to have
some fun with the source code. 

### Local development

To play with the library, create a sample KMM app or use the [example app available on Github](https://github.com/jstarczewski/kstate-samples).
The sample probably will not build, because library must be published to `mavenLocal`.
#### Setup
To publish to `mavenLocal` clone the project and execute the following gradle tasks.
```
./gradlew kstate-generate:publishToMavenLocal kstate-core:publishToMavenLocal
```
[Now the sample app available on Github](https://github.com/jstarczewski/kstate-samples) should build and work properly. 
## Overview

Make state values defined in KMM shared module easy to observe within Jetpack Compose and SwiftUI code with near zero 
boilerplate by implementation of `StateHolder` interface on class providing data to UI.

```mermaid
flowchart RL
    subgraph KMM Shared Module
    StateHolder 
    end
    subgraph iOS/Androd
    View
    end
    StateHolder -- state --> View
    View -- event --> StateHolder
```

### Documentation

Documentation is available [here](https://jstarczewski.github.io/kstate/index.html).

### Example
1. Make your class in KMM shared module a `StateHolder` by implementing `StateHolder` interface via interface delegation
pattern with `StateHolder()` function.
2. Use `StateHolder` DSL functions to declare `State` in shared `StateHolder` with `state` delegate.
```Kotlin
class SimpleViewModel : KmmViewModel(), StateHolder by StateHolder() {

    var message by state("Hello World")
        private set

    fun updateMessage() = viewModelScope.launch {
        delay(500)
        message = "Hello kstate"
    }
}
```
### Android
State changes in `@Composable` functions are reflected because shared `State` on Android platform is exposed as Compose `MutableState`
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
### iOS
Apply `ObservedStateHolder` property wrapper to `SimpleViewModel` to automatically wire state change observation.

**`ObservedStateHolder` is a utility property wrapper that will be generated during [library setup process](#Setup).**
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

### Adding dependencies

To use the `kstate-core` library add it to `dependencies` block inside KMM shared module

```
val commonMain by getting {
    dependencies {
        api("com.jstarczewski:kstate:0.0.2")
    }
}
```

### Generating `.swift` wrappers for iOS

After publication, add `kstate-generate` plugin to your sample project top level `build.gradle.kts` file and configure
generation destination for iOS with
`swiftTemplates` DSL.

```
plugins {
    id("com.jstarczewski.kstate.generate").version("0.0.2")
}

swiftTemplates {

    outputDir = "$expectedRelativeFilesPath"
    sharedModuleName = "common"
}
```

After successfully applying the plugin generate Swift wrappers. Files should appear in `outputDir` specified in
configuration
block.

```
./gradlew generateSwiftTemplates
```

To link generated files with your project, from XCode `File` menu click `Add files` and create group with sources.
When the files are linked, run the `iOS` app to check whether everything builds. Maybe there are additional changes
needed to be done in the files, but
the vision is that after linking generated sources iOS app should build without anny issues.

![img.png](img.png)

![img_1.png](img_1.png)

**The generation process is a one time operation. After successfully generating everything feel free to add it to source
control and remove `kstate-generate` plugin**
