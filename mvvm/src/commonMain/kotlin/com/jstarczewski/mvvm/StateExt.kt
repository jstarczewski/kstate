package com.jstarczewski.mvvm

import com.jstarczewski.state.State
import com.jstarczewski.state.StateHolder

expect fun <T : Any> StateHolder.state(initialValue: T): State<T>

fun <T : Any> T.update(update: T.(T) -> T) = this.update(this)
