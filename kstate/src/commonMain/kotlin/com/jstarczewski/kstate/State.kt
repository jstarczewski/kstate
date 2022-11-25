package com.jstarczewski.kstate

import kotlin.reflect.KProperty

/**
 * State<T : Any> is a [property delegate](https://kotlinlang.org/docs/delegated-properties.html) that
 * represents a wrapper of type T which provides platform specific implementation allowing to observe
 * changes that happen during read and write operations of type T on platforms consuming state
 */
expect open class State<T : Any> {

    open operator fun getValue(thisRef: Any?, property: KProperty<*>): T

    open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}

/**
 * Returns [State] of type T
 *
 * This function can be used with `by` syntax
 *
 * ```
 * var state : String by state("Example")
 * ```
 * @param initialValue initial value of type T
 * @receiver [StateHolder]
 * @return [State] of type T
 */
expect fun <T : Any> StateHolder.state(
    initialValue: T
): State<T>