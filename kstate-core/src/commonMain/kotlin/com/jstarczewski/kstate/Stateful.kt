@file:JvmName("StatefulExt")

package com.jstarczewski.kstate

import kotlin.jvm.JvmName
import kotlin.properties.ReadWriteProperty
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
    initialValue: T,
): Stateful<T>

/**
 * Returns a property delegate for a [Stateful] of value [T] that calls a specified callback function when changed.
 *
 * This function can be used with `by` syntax
 *
 * ```
 * var stateful : String by observableStateful("Example") { oldValue, newValue ->
 *   logger.info("newValue: $newValue, oldValue: $oldValue")
 * }
 * ```
 * To update param created by `stateful` simply write new value to it. This will lead to changes on platform side.
 * Note that write operation to a field is not synchronized and synchronization is on user side.
 * ```
 * var stateful : String by observableStateful("Example") { oldValue, newValue ->
 *   logger.info("newValue: $newValue, oldValue: $oldValue")
 * }
 *
 * fun update() {
 *     stateful = "Other example" // newValue: Other example, oldValue: Example
 * }
 * ```
 *
 * @param initialValue initial value of type T
 * @param onChange the callback which is called after the change of the property is made.
 * The value of the property has already been changed when this callback is invoked.
 * @receiver [StateHolder]
 * @return [Stateful] of type T
 */
inline fun <T : Any> StateHolder.observableStateful(
    initialValue: T,
    crossinline onChange: (oldValue: T, newValue: T) -> Unit,
): ReadWriteProperty<StateHolder, T> = object : ObservableStateful<T>(this, initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChange(oldValue, newValue)
}
