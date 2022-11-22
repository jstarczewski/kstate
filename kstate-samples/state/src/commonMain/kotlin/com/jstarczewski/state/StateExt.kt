package com.jstarczewski.state

expect fun <T : Any> StateHolder.state(
    initialValue: T
): State<T>