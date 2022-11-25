package com.jstarczewski.kstate

interface StateHolder {

    val binder: Binder
}

fun StateHolder(): StateHolder = object : StateHolder {

    override val binder: Binder = Binder()
}