package com.jstarczewski.kstate

actual open class Binder {

    open var onChanged: () -> Unit = {}
}