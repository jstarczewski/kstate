package com.jstarczewski.kmm

import androidx.lifecycle.viewModelScope
import com.jstarczewski.kstate.StateHolder
import kotlinx.coroutines.CoroutineScope

private val androidx.lifecycle.ViewModel.scope
    get() = this.viewModelScope

actual abstract class KmmViewModel actual constructor() :
    androidx.lifecycle.ViewModel(), StateHolder {

    final override fun onCleared() {
        onClear()
        super.onCleared()
    }

    actual open fun onClear() = Unit

    actual val viewModelScope: CoroutineScope
        get() = scope
}