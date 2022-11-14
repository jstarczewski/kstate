package com.jstarczewski.mvvm

import com.jstarczewski.state.State
import com.jstarczewski.state.StateHolder

actual fun <T : Any> StateHolder.state(initialValue: T): State<T> =
    State(initialValue) { objectWillChange() }
