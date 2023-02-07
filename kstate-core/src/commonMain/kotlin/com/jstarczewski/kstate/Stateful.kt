package com.jstarczewski.kstate

import kotlin.reflect.KProperty

/**
 * Stateful<T : Any> is a [property delegate](https://kotlinlang.org/docs/delegated-properties.html) that
 * represents a wrapper of type T which provides platform specific implementation allowing to receive update on target
 * platforms that happen during write operations from KMM shared module.
 */
expect open class Stateful<T : Any> {

    open operator fun getValue(thisRef: Any?, property: KProperty<*>): T

    open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}

/**
 * Returns [Stateful] of type T
 *
 * This function can be used with `by` syntax
 *
 * ```
 * var state : String by state("Example")
 * ```
 * @param initialValue initial value of type T
 * @receiver [StateHolder]
 * @return [Stateful] of type T
 */
expect fun <T : Any> StateHolder.stateful(
    initialValue: T
): Stateful<T>
