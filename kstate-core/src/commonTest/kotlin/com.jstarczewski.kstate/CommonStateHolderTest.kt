package com.jstarczewski.kstate

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CommonStateHolderTest {

    @Test
    fun initialIsLoadingStateIsEqualInitialValueOfFalse() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by stateful(false)
        }
        assertFalse(stateHolder.isLoading)
    }

    @Test
    fun isLoadingStateChangedFromFalseToTrueChangesToTrue() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by stateful(false)
        }
        stateHolder.isLoading = true
        assertTrue(stateHolder.isLoading)
    }
}