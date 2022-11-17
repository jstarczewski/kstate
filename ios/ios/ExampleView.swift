import SwiftUI
import common

struct ExampleView: View {
    
    @ObservedStateHolder var viewModel =  ExampleViewModel(config: MvvmConfig())
    
	var body: some View {
        Text("Counter = \(viewModel.state.counter)")
            .onTapGesture {
                viewModel.updateState()
            }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ExampleView()
	}
}

