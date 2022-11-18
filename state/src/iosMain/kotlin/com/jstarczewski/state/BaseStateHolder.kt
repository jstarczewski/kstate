package com.jstarczewski.state

actual abstract class BaseStateHolder : StateHolder {

    override var objectWillChange: () -> Unit = {}
}