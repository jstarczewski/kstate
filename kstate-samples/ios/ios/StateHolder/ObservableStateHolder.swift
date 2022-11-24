import Foundation
import common

class ObservableStateHolder<StateHolder>: ObservableObject where StateHolder: common.KstateStateHolder {

    var stateHolder: StateHolder

    init(_ stateHolder: StateHolder) {
        self.stateHolder = stateHolder
        self.stateHolder.stateFactory.objectWillChange = { [weak self] in
            DispatchQueue.main.async {
                self?.objectWillChange.send()
            }
        }
    }
}
