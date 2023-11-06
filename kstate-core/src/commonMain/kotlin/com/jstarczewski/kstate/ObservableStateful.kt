package com.jstarczewski.kstate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Implements a core logic of a [Stateful] property that calls callback functions when changed.
 */
abstract class ObservableStateful<T : Any>(receiver: StateHolder, initialState: T) : ReadWriteProperty<StateHolder, T> {

    private var value by receiver.stateful(initialState)

    /**
     *  The callback that is triggered just before an attempt to update the state value.
     *  When this callback is called, the state's value has not yet changed.
     *  The value of the property is being set to the new value if the callback returns true;
     *  otherwise, the new value gets ignored and the state is left at its previous value.
     */
    protected open fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T): Boolean = true

    /**
     * The callback that is called after a property update is made.
     * When this callback is used, the property's value has already changed.
     */
    protected open fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {}

    override fun getValue(thisRef: StateHolder, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: StateHolder, property: KProperty<*>, value: T) {
        val oldValue = this.value
        if (!beforeChange(property, oldValue, value)) {
            return
        }
        this.value = value
        afterChange(property, oldValue, value)
    }
}
