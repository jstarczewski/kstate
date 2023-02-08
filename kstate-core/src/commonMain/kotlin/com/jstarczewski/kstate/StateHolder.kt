package com.jstarczewski.kstate

/**
 * Classes that aim to provide a [Stateful] that platforms can observe should implement this
 * interface to allow easy binding
 * using auto-generated files for iOS.
 * Implementing [StateHolder] also provides a DSL for creating various
 * [Stateful] values, for example with [stateful] function.
 *
 * Recommended way to implement [StateHolder] interface is to us delegation pattern.
 * ```
 * class ExampleClassWithState : StateHolder by StateHolder()
 * ```
 *
 * @property[Binder] contains platform specific fields that make it easy to integrate the pattern on target platform.
 * @see[stateful]
 */
interface StateHolder {

    val binder: Binder
}

/**
 * Provides a short way to a class can conform to [StateHolder] interface.
 * Creates a default [StateHolder] implementation. Mainly used with delegation pattern [StateHolder] for more
 * information.
 */
fun StateHolder(): StateHolder = object : StateHolder {

    override val binder: Binder = Binder()
}
