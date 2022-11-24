package com.jstarczewski.kmm

import com.jstarczewski.kstate.StateHolder
import kotlinx.coroutines.CoroutineScope

expect abstract class KmmViewModel constructor() : StateHolder {

    val viewModelScope: CoroutineScope

    open fun onClear()
}