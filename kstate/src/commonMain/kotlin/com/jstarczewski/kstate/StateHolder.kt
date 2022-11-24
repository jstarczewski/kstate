package com.jstarczewski.kstate

interface StateHolder {

    val stateFactory: StateFactory
}

fun StateHolder(): StateHolder = object : StateHolder {

    override val stateFactory: StateFactory = StateFactory()
}