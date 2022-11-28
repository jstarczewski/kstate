import SwiftUI
import common

struct SimpleView: View {
    
    @ObservedStateHolder private var viewModel = SimpleViewModel()
    @Binding var move: Bool
    
	var body: some View {
        Text("First \(viewModel.message)")
            .onTapGesture {
                move = !move
            }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
        SimpleView(move: .constant(false))
	}
}

struct OtherSimpleView: View {
    
    @ObservedStateHolder private var viewModel = SimpleViewModel()
    @Binding var move: Bool
    
    var body: some View {
        Text("Other \(viewModel.message)")
            .onTapGesture {
                move = !move
            }
    }

}
