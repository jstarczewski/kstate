package com.jstarczewski.kstate

expect fun <T : Any> StateHolder.state(
    initialValue: T
): State<T>