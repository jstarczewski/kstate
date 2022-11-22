package com.jstarczewski.kstate

actual abstract class BaseStateHolder : StateHolder {

    override var objectWillChange: () -> Unit = {}
}