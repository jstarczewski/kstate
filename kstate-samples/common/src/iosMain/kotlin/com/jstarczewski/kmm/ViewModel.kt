package com.jstarczewski.kmm

import com.jstarczewski.kstate.StateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

actual abstract class ViewModel actual constructor(config: Config) : StateHolder {

    actual val viewModelScope: CoroutineScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    override var objectWillChange: () -> Unit = {}
        set(value) {
            value()
            field = value
        }

    actual open fun onClear() {
        viewModelScope.cancel()
        objectWillChange = {}
    }
}