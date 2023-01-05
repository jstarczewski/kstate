package com.jstarczewski.kstate

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CommonStateHolderTest {

    @Test
    fun `initial isLoading state is equal initial value of false`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        assertFalse(stateHolder.isLoading)
    }

    @Test
    fun `isLoading state changed from false to true changes to true`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        stateHolder.isLoading = true
        assertTrue(stateHolder.isLoading)
    }
}