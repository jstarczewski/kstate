//
//  StateViewModel.swift
//  ios
//
//  Created by Jan Starczewski on 14/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import common

@propertyWrapper
struct StateViewModel<ViewModel>: DynamicProperty where ViewModel: MvvmViewModel {

    @StateObject private var viewModelObservable: ObservableViewModel<ViewModel>

    init(wrappedValue: ViewModel) {
        _viewModelObservable = StateObject(wrappedValue: ObservableViewModel(wrappedValue))
    }

    var wrappedValue: ViewModel {
        get { return viewModelObservable.viewModel }
        set { viewModelObservable.viewModel = newValue }
    }

    var projectedValue: ObservedObject<ObservableViewModel<ViewModel>>.Wrapper {
        self.$viewModelObservable
    }
}
