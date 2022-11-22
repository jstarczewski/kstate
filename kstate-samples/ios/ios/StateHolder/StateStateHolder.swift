//
//  StateStateHolder.swift
//  ios
//
//  Created by Jan Starczewski on 17/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import common

@propertyWrapper
struct StateStateHolder<StateHolder>: DynamicProperty where StateHolder: common.CommonStateHolder {

    @StateObject private var stateHolderObservable: ObservableStateHolder<StateHolder>

    init(wrappedValue: StateHolder) {
        _stateHolderObservable = StateObject(wrappedValue: ObservableStateHolder(wrappedValue))
    }

    var wrappedValue: StateHolder {
        get { return stateHolderObservable.stateHolder }
        set { stateHolderObservable.stateHolder = newValue }
    }

    var projectedValue: ObservedObject<ObservableStateHolder<StateHolder>>.Wrapper {
        self.$stateHolderObservable
    }
}
