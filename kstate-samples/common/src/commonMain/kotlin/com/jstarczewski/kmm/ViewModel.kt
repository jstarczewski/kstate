package com.jstarczewski.kmm

import com.jstarczewski.kstate.StateHolder
import kotlinx.coroutines.CoroutineScope

expect abstract class ViewModel constructor(config: Config) : StateHolder {

    val viewModelScope: CoroutineScope

    open fun onClear()
}