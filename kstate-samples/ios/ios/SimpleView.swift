import SwiftUI
import common

struct SimpleView: View {
    
    @ObservedStateHolder var viewModel = SimpleViewModel()
    
	var body: some View {
        Text(viewModel.message)
            .onTapGesture {
                viewModel.updateMessage()
            }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		SimpleView()
	}
}

