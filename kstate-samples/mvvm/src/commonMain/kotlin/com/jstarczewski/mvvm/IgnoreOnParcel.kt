package com.jstarczewski.mvvm

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
expect annotation class IgnoreOnParcel()
