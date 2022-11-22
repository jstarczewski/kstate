package com.jstarczewski.kmm

import com.jstarczewski.kstate.State
import com.jstarczewski.kstate.StateHolder

actual fun <T : Any> StateHolder.saveableState(initialValue: T): State<T> =
    State(initialValue) { objectWillChange() }
