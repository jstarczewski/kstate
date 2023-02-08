package com.jstarczewski.kstate

/**
 * Actual implementation of [Binder]
 *
 * Property [objectWillChange] should be set to
 * [ObservableObject's](https://developer.apple.com/documentation/combine/observableobject)
 * [objectWillChange](https://developer.apple.com/documentation/combine/observableobject/objectwillchange-5gopl)
 * property on iOS side, available in context of
 * [ObservableObject](https://developer.apple.com/documentation/combine/observableobject) protocol.
 */
actual open class Binder {

    /**
     * On write operation the value is invoked to deliver updates of all stored [Stateful] properties withing a context of
     * [StateHolder] the [Binder] is associated with
     */
    open var objectWillChange: () -> Unit = {}
}
