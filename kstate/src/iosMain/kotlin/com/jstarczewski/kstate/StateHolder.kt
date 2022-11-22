package com.jstarczewski.kstate

actual interface StateHolder {

    var objectWillChange: () -> Unit
}