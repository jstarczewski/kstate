package com.jstarczewski.mvvm

import androidx.lifecycle.SavedStateHandle.Companion.validateValue
import com.jstarczewski.state.State
import com.jstarczewski.state.StateHolder

actual fun <T : Any> StateHolder.state(initialValue: T): State<T> {
    return if (this is CanPersistState && validateValue(initialValue)) {
        State(savedStateHandle[key()] ?: initialValue) {
            savedStateHandle[key()] = it
        }
    } else {
        State(initialValue)
    }
}

private fun StateHolder.key() = this::class.java.simpleName
