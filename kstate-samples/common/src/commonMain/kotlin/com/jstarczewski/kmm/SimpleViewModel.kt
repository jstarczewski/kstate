package com.jstarczewski.kmm

import com.jstarczewski.kstate.StateHolder
import com.jstarczewski.kstate.state
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SimpleViewModel : KmmViewModel(), StateHolder by StateHolder() {

    var message by state("Hello World")
        private set

    var isLoading by state(false)
        private set

    fun updateMessage() = viewModelScope.launch {
        isLoading = true
        delay(500)
        message = "Hello kstate"
        isLoading = false
    }
}