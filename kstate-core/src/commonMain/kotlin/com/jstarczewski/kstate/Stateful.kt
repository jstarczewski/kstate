package com.jstarczewski.kstate

import kotlin.reflect.KProperty

/**
 * [Stateful] is a [property delegate](https://kotlinlang.org/docs/delegated-properties.html) that
 * represents a wrapper of type T which provides platform specific implementation allowing to receive value updates on target
 * platforms. The value wrapped by [Stateful] is updated after write operation.
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
 * var stateful : String by stateful("Example")
 * ```
 * To update param created by `stateful` simply write new value to it. This will lead to changes on platform side.
 * Note that write operation to a field is not synchronized and synchronization is on user side.
 * ```
 * var stateful : String by stateful("Example")
 *
 * fun update() {
 *     stateful = "Other example"
 * }
 * ```
 *
 * @param initialValue initial value of type T
 * @receiver [StateHolder]
 * @return [Stateful] of type T
 */
expect fun <T : Any> StateHolder.stateful(
    initialValue: T
): Stateful<T>
