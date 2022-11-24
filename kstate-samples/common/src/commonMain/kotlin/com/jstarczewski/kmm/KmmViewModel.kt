package com.jstarczewski.kmm

import kotlinx.coroutines.CoroutineScope

expect abstract class KmmViewModel constructor() {

    val viewModelScope: CoroutineScope

    open fun onClear()
}