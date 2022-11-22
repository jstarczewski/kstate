package com.jstarczewski.kmm

import com.jstarczewski.kstate.BaseStateHolder
import com.jstarczewski.kstate.state

class DomainObject : BaseStateHolder() {

    var name by state("Hello World")
        private set

    fun updateName() {
        name = "Hello"
    }
}