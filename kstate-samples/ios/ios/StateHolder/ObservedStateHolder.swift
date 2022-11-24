import Foundation
import SwiftUI
import common

@propertyWrapper
struct ObservedStateHolder<StateHolder>: DynamicProperty where StateHolder: common.KstateStateHolder {

    @ObservedObject private var stateHolderObservable: ObservableStateHolder<StateHolder>

    init(wrappedValue: StateHolder) {
        self.stateHolderObservable = ObservableStateHolder(wrappedValue)
    }

    var wrappedValue: StateHolder {
        get { return stateHolderObservable.stateHolder }
        set { stateHolderObservable.stateHolder = newValue }
    }

    var projectedValue: ObservedObject<ObservableStateHolder<StateHolder>>.Wrapper {
        self.$stateHolderObservable
    }
}