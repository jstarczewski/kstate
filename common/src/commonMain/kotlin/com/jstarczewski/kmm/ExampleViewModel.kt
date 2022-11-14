package com.jstarczewski.kmm

import com.jstarczewski.mvvm.Config
import com.jstarczewski.mvvm.IgnoreOnParcel
import com.jstarczewski.mvvm.Parcelable
import com.jstarczewski.mvvm.Parcelize
import com.jstarczewski.mvvm.ViewModel
import com.jstarczewski.mvvm.state
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Parcelize
data class ExampleViewModelState(
    val isLoading: Boolean = false,
    val counter: Int = 0,
    val title: String = "Title",
    val items: List<Int> = emptyList(),
    @IgnoreOnParcel
    val otherCounter: Int = 0
) : Parcelable

class ExampleViewModel(config: Config) : ViewModel(config) {

    var state: ExampleViewModelState by state(ExampleViewModelState())
        private set

    private val mutex = Mutex()

    fun updateState() = viewModelScope.launch(Dispatchers.Default) {
        coroutineScope {
            while (isActive) {
                delay(300)
                repeat(30) {
                    launch {
                        mutex.withLock {
                            state = state.copy(counter = state.counter + 1)
                        }
                    }
                }
            }
        }
    }
}