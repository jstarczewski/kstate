package com.jstarczewski.mvvm

import com.jstarczewski.state.State

actual class SaveableState<T : Any>(
    initialValue: T
) : State<T>(initialValue)