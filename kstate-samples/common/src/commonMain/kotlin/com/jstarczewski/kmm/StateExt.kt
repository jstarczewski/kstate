package com.jstarczewski.kmm

import com.jstarczewski.kstate.State
import com.jstarczewski.kstate.StateHolder

expect fun <T : Any> StateHolder.saveableState(initialValue: T): State<T>

fun <T : Any> T.update(update: T.(T) -> T) = this.update(this)
