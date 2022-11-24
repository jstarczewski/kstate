package com.jstarczewski.kmm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

actual abstract class KmmViewModel actual constructor() {

    actual val viewModelScope: CoroutineScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    actual open fun onClear() {
        viewModelScope.cancel()
    }
}