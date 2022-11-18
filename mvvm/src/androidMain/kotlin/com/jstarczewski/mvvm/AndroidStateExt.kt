package com.jstarczewski.mvvm

import androidx.lifecycle.SavedStateHandle.Companion.validateValue
import com.jstarczewski.state.State
import com.jstarczewski.state.StateHolder

actual fun <T : Any> StateHolder.saveableState(initialValue: T): State<T> {
    require(this is CanPersistState) {
        "Cannot save state from something which does not conform to `CanPersistState` interface"
    }
    return if (validateValue(initialValue)) {
        SaveableState(
            initialValue = initialValue,
            holderKey = key(),
            getSavedValue = { key -> savedStateHandle[key] },
            saveValue = { key, value -> savedStateHandle[key] = value }
        )
    } else {
        throw IllegalArgumentException(
            "Cannot persist state of value which cannot be store in Parcel"
        )
    }
}

private fun StateHolder.key() = this::class.java.simpleName
