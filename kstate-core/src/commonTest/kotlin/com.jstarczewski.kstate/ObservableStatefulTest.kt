package com.jstarczewski.kstate

import kotlin.reflect.KProperty
import kotlin.test.Test
import kotlin.test.assertEquals

class ObservableStatefulTest {

    @Test
    fun `should call afterChange when state holder set a new value`() {
        var callCounter = 0
        val stateHolder = object : StateHolder by StateHolder() {
            var observableStateful by object : ObservableStateful<String>(this, "") {
                override fun afterChange(property: KProperty<*>, oldValue: String, newValue: String) {
                    callCounter++
                }
            }
        }
        stateHolder.observableStateful = "test"
        assertEquals(
            expected = 1,
            actual = callCounter,
            message = "afterChange should be called once",
        )
    }

    @Test
    fun `should set a new value when state holder set a new value and beforeChange return true`() {
        val initialValue = "initial"
        val theNewValue = "test"
        var callCounter = 0
        var actualOldValue = ""
        var actualNewValue = ""
        val stateHolder = object : StateHolder by StateHolder() {
            var observableStateful by object : ObservableStateful<String>(this, initialValue) {
                override fun beforeChange(property: KProperty<*>, oldValue: String, newValue: String): Boolean {
                    actualNewValue = newValue
                    actualOldValue = oldValue
                    callCounter++
                    return true
                }
            }
        }
        stateHolder.observableStateful = theNewValue
        assertEquals(
            expected = 1,
            actual = callCounter,
            message = "beforeChange should be called once",
        )
        assertEquals(
            expected = theNewValue,
            actual = stateHolder.observableStateful,
            message = "current value does not equal to the new value",
        )
        assertEquals(
            expected = initialValue,
            actual = actualOldValue,
            message = "old value does not equal to the initial value",
        )
        assertEquals(
            expected = theNewValue,
            actual = actualNewValue,
            message = "new value does not equal to the new value",
        )
    }

    @Test
    fun `should not set a new value when state holder set a new value and beforeChange returns false`() {
        val initialValue = "initial"
        var callCounter = 0
        val theNewValue = "test"
        var actualOldValue = ""
        var actualNewValue = ""
        val stateHolder = object : StateHolder by StateHolder() {
            var observableStateful by object : ObservableStateful<String>(this, initialValue) {
                override fun beforeChange(property: KProperty<*>, oldValue: String, newValue: String): Boolean {
                    actualNewValue = newValue
                    actualOldValue = oldValue
                    callCounter++
                    return false
                }
            }
        }
        stateHolder.observableStateful = "test"
        assertEquals(
            expected = 1,
            actual = callCounter,
            message = "beforeChange should be called once",
        )
        assertEquals(
            expected = initialValue,
            actual = stateHolder.observableStateful,
            message = "beforeChange should reject the new value and remain as it were",
        )
        assertEquals(
            expected = initialValue,
            actual = actualOldValue,
            message = "old value does not equal to the initial value",
        )
        assertEquals(
            expected = theNewValue,
            actual = actualNewValue,
            message = "new value does not equal to the new value",
        )
    }
}
