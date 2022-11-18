import SwiftUI
import common

struct ExampleView: View {
    
    @ObservedStateHolder var domainObject = DomainObject()
    
	var body: some View {
        Text("Name = \(domainObject.name)")
            .onTapGesture {
                domainObject.updateName()
            }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ExampleView()
	}
}

