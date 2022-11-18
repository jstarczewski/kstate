//
//  ObservedViewModel.swift
//  ios
//
//  Created by Jan Starczewski on 14/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import common

@propertyWrapper
struct ObservedViewModel<ViewModel>: DynamicProperty where ViewModel: MvvmViewModel {

    @ObservedObject private var viewModelObservable: ObservableViewModel<ViewModel>

    init(wrappedValue: ViewModel) {
        self.viewModelObservable = ObservableViewModel(wrappedValue)
    }

    var wrappedValue: ViewModel {
        get { return viewModelObservable.viewModel }
        set { viewModelObservable.viewModel = newValue }
    }

    var projectedValue: ObservedObject<ObservableViewModel<ViewModel>>.Wrapper {
        self.$viewModelObservable
    }
}
