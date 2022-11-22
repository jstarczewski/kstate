//
//  ObservableStateHolder.swift
//  ios
//
//  Created by Jan Starczewski on 17/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import common

class ObservableStateHolder<StateHolder>: ObservableObject where StateHolder: common.KstateStateHolder {

    var stateHolder: StateHolder

    init(_ stateHolder: StateHolder) {
        self.stateHolder = stateHolder
        self.stateHolder.objectWillChange = { [weak self] in
            DispatchQueue.main.async {
                self?.objectWillChange.send()
            }
        }
    }
}
