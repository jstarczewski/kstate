package com.jstarczewski.kstate

import kotlin.reflect.KProperty

expect open class State<T : Any> {

    open operator fun getValue(thisRef: Any?, property: KProperty<*>): T

    open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}