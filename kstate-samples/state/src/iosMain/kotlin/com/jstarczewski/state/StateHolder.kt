package com.jstarczewski.state

actual interface StateHolder {

    var objectWillChange: () -> Unit
}