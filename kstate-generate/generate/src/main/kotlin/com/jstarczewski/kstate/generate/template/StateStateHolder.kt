package com.jstarczewski.kstate.generate.template

object StateStateHolder : FileToGenerate() {

    override val fileName: String = "StateStateHolder.swift"

    override val fileContent: String = """
       import Foundation
       import SwiftUI
       import &PROJECT_TOKEN

       @propertyWrapper
       struct StateStateHolder<StateHolder>: DynamicProperty where StateHolder: &PROJECT_TOKEN.KstateStateHolder {

       @StateObject private var stateHolderObservable: ObservableStateHolder<StateHolder>

       init(wrappedValue: StateHolder) {
           _stateHolderObservable = StateObject(wrappedValue: ObservableStateHolder(wrappedValue))
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