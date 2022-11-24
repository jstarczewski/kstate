package com.jstarczewski.kstate

actual open class StateFactory {

    var objectWillChange: () -> Unit = {}
}