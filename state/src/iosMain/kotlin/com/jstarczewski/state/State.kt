package com.jstarczewski.state

import kotlin.reflect.KProperty

actual class State<T : Any>(
    private var initialValue: T
) {

    private var objectWillChange: () -> Unit = {}

    constructor(
        initialValue: T,
        objectWillChange: () -> Unit
    ) : this(initialValue) {
        this.objectWillChange = objectWillChange
    }

    actual operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return initialValue
    }

    actual operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        initialValue = value
        objectWillChange()
    }
}