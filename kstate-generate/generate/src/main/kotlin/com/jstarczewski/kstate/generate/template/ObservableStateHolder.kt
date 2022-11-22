package com.jstarczewski.kstate.generate.template

object ObservableStateHolder : FileToGenerate() {

    override val fileName: String = "ObservableStateHolder.swift"

    override val fileContent: String = """
        import Foundation
        import &PROJECT_TOKEN

        class ObservableStateHolder<StateHolder>: ObservableObject where StateHolder: &PROJECT_TOKEN.KstateStateHolder {

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
    """.trimIndent()
}