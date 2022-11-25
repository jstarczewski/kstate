package com.jstarczewski.kstate

import kotlin.reflect.KProperty

actual open class State<T : Any>(
    private var initialValue: T,
    private val objectWillChange: () -> Unit
) {

    actual open operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return initialValue
    }

    actual open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        initialValue = value
        objectWillChange()
    }
}

actual fun <T : Any> StateHolder.state(initialValue: T): State<T> =
    State(initialValue) { binder.objectWillChange() }
