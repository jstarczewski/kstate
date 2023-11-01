package com.jstarczewski.kstate

import kotlin.reflect.KProperty

actual open class Stateful<T : Any>(
    private var initialValue: T,
    private val onChanged: () -> Unit
) {

    actual open operator fun getValue(thisRef: Any?, property: KProperty<*>): T = initialValue

    actual open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        initialValue = value
        onChanged()
    }
}

actual fun <T : Any> StateHolder.stateful(initialValue: T): Stateful<T> =
    Stateful(initialValue) { binder.onChanged() }