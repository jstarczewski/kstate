package com.jstarczewski.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.reflect.KProperty

actual class State<T : Any>(
    initialValue: T,
    private val saveValue: (value: T) -> Unit = { }
) {

    var value by mutableStateOf(initialValue)

    actual operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    actual operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        saveValue(value)
        this.value = value
    }
}