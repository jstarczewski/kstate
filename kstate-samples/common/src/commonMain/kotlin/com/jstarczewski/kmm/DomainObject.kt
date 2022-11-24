package com.jstarczewski.kmm

import com.jstarczewski.kstate.BaseStateHolder
import com.jstarczewski.kstate.state
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DomainObject : BaseStateHolder() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    var name by state("Hello World")
        private set

    var isLoading by state(false)
        private set

    fun updateName() =
        coroutineScope.launch {
            isLoading = true
            delay(500)
            name = "Perform update"
            isLoading = false
        }
}