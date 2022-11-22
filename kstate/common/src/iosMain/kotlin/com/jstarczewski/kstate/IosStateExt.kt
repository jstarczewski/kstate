package com.jstarczewski.kstate

actual fun <T : Any> StateHolder.state(initialValue: T): State<T> =
    State(initialValue) { objectWillChange() }