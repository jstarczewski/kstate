package com.jstarczewski.kstate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.reflect.KProperty

/**
 * Actual implementation of [Stateful] for iOS platform
 *
 * @property[initialValue] initial value that is wrapped by [Stateful]
 * @property[objectWillChange] callback that is invoked after every write to the [initialValue] property to signal that
 * the values has changed.
 */
actual open class Stateful<T : Any>(
    initialValue: T,
    private val objectWillChange: () -> Unit
) {

    protected var value by mutableStateOf(initialValue)
    actual open operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    actual open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        objectWillChange()
    }
}

/**
 * Creates [Stateful] with provided initial value and callback set to one defined
 * in [StateHolder]'s [Binder] object.
 *
 * @param[initialValue] initial value that will be wrapped by [Stateful]
 * @return[Stateful] wrapping [initialValue]
 */
actual fun <T : Any> StateHolder.stateful(initialValue: T): Stateful<T> =
    Stateful(initialValue) { binder.objectWillChange() }
