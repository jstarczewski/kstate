package com.jstarczewski.kstate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.reflect.KProperty

/**
 * Actual implementation of [Stateful] on Android platform
 *
 * @param[initialValue] param that is going to be initial value of [value] `mutableState`
 */
actual open class Stateful<T : Any>(
    initialValue: T,
) {

    /**
     * Holds the stateful that is observed within the `@Composable` functions on Android platform. It's value changes when
     * write operation is performed to [Stateful] and then the value is reflected on target platform after `Snapshot`
     * is applied.
     */
    protected var value by mutableStateOf(initialValue)

    actual open operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    actual open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}

/**
 * Creates a [Stateful]
 *
 * @param[initialValue] initial value that will be wrapped by [Stateful]
 * @return[Stateful] wrapping [initialValue]
 */
actual fun <T : Any> StateHolder.stateful(initialValue: T): Stateful<T> =
    Stateful(initialValue)
