//
//  ObservableViewModel.swift
//  ios
//
//  Created by Jan Starczewski on 14/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import common

class ObservableViewModel<ViewModel>: ObservableObject where ViewModel: MvvmViewModel {

    var viewModel: ViewModel

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
        self.viewModel.objectWillChange = { [weak self] in
            DispatchQueue.main.async {
                self?.objectWillChange.send()
            }
        }
    }

    deinit {
        viewModel.onClear()
    }
}
