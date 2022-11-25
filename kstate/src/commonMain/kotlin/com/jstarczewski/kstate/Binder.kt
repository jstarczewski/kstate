package com.jstarczewski.kstate

/**
 * Object associated with [StateHolder]. Actual implementations contain properties that are needed
 * for a particular platform to allow state observation.
 */
expect open class Binder()