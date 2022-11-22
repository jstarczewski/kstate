package com.jstarczewski.mvvm

import com.jstarczewski.state.StateHolder
import kotlinx.coroutines.CoroutineScope

expect abstract class ViewModel constructor(config: Config) : StateHolder {

    val viewModelScope: CoroutineScope

    open fun onClear()
}