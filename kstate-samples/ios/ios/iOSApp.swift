import SwiftUI

@main
struct iOSApp: App {
    
    @State var move: Bool = true
    
	var body: some Scene {
		WindowGroup {
            if(move) {
                SimpleView(move: $move)
            } else {
                OtherSimpleView(move: $move)
            }
        }
	}
}
