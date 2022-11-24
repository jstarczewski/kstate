package com.jstarczewski.kstate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.reflect.KProperty

actual open class State<T : Any>(
    initialValue: T,
) {

    protected var value by mutableStateOf(initialValue)

    actual open operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    actual open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}

actual fun <T : Any> StateHolder.state(initialValue: T): State<T> =
    State(initialValue)
