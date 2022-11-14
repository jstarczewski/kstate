package com.jstarczewski.kmm

import com.jstarczewski.mvvm.Config
import com.jstarczewski.mvvm.IgnoreOnParcel
import com.jstarczewski.mvvm.Parcelable
import com.jstarczewski.mvvm.Parcelize
import com.jstarczewski.mvvm.ViewModel
import com.jstarczewski.mvvm.state
import com.jstarczewski.mvvm.update
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

interface HasLoadingState {

    val isLoading: Boolean
}

@Parcelize
data class ExampleViewModelState(
    override val isLoading: Boolean = false,
    val counter: Int = 0,
    val title: String = "Title",
    val items: List<Int> = emptyList(),
    @IgnoreOnParcel
    val otherCounter: Int = 0
) : Parcelable, HasLoadingState

class ExampleViewModel(config: Config) : ViewModel(config) {

    private var _state: ExampleViewModelState by state(ExampleViewModelState())

    val state
        get() = _state

    fun updateState() {
        viewModelScope.launch(Dispatchers.Default) {
            coroutineScope {
                while (isActive) {
                    delay(400)
                    _state = _state.update {
                        copy(counter = counter + 1)
                    }
                    print("State = ${_state.counter} \n")
                }
            }
        }
    }
}