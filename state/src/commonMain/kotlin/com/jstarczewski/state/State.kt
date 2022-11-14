package com.jstarczewski.state

import kotlin.reflect.KProperty

expect class State<T : Any> {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}