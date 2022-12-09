package com.jstarczewski.kstate

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IosStateHolderTest {

    @Test
    fun `initial isLoading state is equal initial value of false`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        assertFalse(stateHolder.isLoading)
    }

    @Test
    fun `binder callback is invoked on isLoading state change to true`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        stateHolder.binder.objectWillChange = { assertTrue(true) }
        stateHolder.isLoading = true
    }

    @Test
    fun `binder callback is invoked two time when write operations are made to isLoading state`() {
        var counter = 0
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        stateHolder.binder.objectWillChange = { counter++ }
        stateHolder.isLoading = true
        stateHolder.isLoading = false
        assertEquals(2, counter)
    }

    @Test
    fun `binder callback is invoked two time when write operation to two separate state values is made`() {
        var counter = 0
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
            var errorCount by state(0)
        }
        stateHolder.binder.objectWillChange = { counter++ }
        stateHolder.isLoading = true
        stateHolder.errorCount = 213
        assertEquals(2, counter)
    }

    @Test
    fun `binder can be overridden to custom implementation`() {
        val customBinder = object : Binder() {

            var counter = 0

            override var objectWillChange: () -> Unit = {
                super.objectWillChange()
                counter++
            }
        }

        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)

            override val binder: Binder = customBinder
        }

        stateHolder.isLoading = true
        assertEquals(1, customBinder.counter)
        assertEquals(customBinder, stateHolder.binder)
    }
}