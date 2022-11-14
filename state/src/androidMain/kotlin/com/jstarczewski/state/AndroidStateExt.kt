package com.jstarczewski.state

actual fun <T : Any> StateHolder.state(initialValue: T): State<T> =
    State(initialValue)