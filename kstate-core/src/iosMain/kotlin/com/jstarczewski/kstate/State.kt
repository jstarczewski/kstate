package com.jstarczewski.kstate

import kotlin.reflect.KProperty

/**
 * Actual implementation of [State] for iOS platform
 *
 * @property[initialValue] initial value that is wrapped by [State]
 * @property[objectWillChange] callback that is invoked after every write to the [initialValue] property to signal that
 * the values has changed.
 */
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

/**
 * Creates [State] with provided initial value and callback set to one defined
 * in [StateHolder]'s [Binder] object.
 *
 * @param[initialValue] initial value that will be wrapped by [State]
 * @return[State] wrapping [initialValue]
 */
actual fun <T : Any> StateHolder.state(initialValue: T): State<T> =
    State(initialValue) { binder.objectWillChange() }
