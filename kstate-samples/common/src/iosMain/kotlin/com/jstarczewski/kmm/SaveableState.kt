package com.jstarczewski.kmm

import com.jstarczewski.kstate.State

actual class SaveableState<T : Any>(
    initialValue: T,
) : State<T>(initialValue, {})