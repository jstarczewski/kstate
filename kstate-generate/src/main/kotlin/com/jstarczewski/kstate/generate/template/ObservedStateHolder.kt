package com.jstarczewski.kstate.generate.template

object ObservedStateHolder : FileToGenerate() {

    override val fileName = "ObservedStateHolder.swift"

    override val fileContent = """
    import Foundation
    import SwiftUI
    import &PROJECT_TOKEN

    @propertyWrapper
    struct ObservedStateHolder<StateHolder>: DynamicProperty where StateHolder: &PROJECT_TOKEN.KstateStateHolder {

        @ObservedObject private var stateHolderObservable: ObservableStateHolder<StateHolder>

        init(wrappedValue: StateHolder) {
            self.stateHolderObservable = ObservableStateHolder(wrappedValue)
        }

        var wrappedValue: StateHolder {
            get { return stateHolderObservable.stateHolder }
            set { stateHolderObservable.stateHolder = newValue }
        }

        var projectedValue: ObservedObject<ObservableStateHolder<StateHolder>>.Wrapper {
            self.${'$'}stateHolderObservable
        }
    }
    """.trimIndent()
}
