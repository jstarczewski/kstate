package com.jstarczewski.kmm

import com.jstarczewski.state.BaseStateHolder
import com.jstarczewski.state.state

class DomainObject : BaseStateHolder() {

    var name by state("Hello World")
        private set

    fun updateName() {
        name = "Hello"
    }
}