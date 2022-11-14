package com.jstarczewski.mvvm

import androidx.lifecycle.SavedStateHandle

interface CanPersistState {

    val savedStateHandle: SavedStateHandle
}
