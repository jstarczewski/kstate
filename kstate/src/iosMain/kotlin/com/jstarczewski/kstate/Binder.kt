package com.jstarczewski.kstate

/**
 * Actual implementation of [Binder]
 *
 * @property[objectWillChange] should be set to
 * [ObservableObject](https://developer.apple.com/documentation/combine/observableobject)
 * [objectWillChange](https://developer.apple.com/documentation/combine/observableobject/objectwillchange-5gopl)
 * property from iOS side, available in context of
 * [ObservableObject](https://developer.apple.com/documentation/combine/observableobject) protocol
 */
actual open class Binder {

    /**
     * On write operation the value is invoked to deliver updates of all stored [State] properties withing a context of
     * [StateHolder] the [Binder] is associated with
     */
    var objectWillChange: () -> Unit = {}
        set(value) {
            value()
            field = value
        }
}
