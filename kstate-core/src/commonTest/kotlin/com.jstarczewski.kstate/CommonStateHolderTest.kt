package com.jstarczewski.kstate

import kotlin.test.Test
import kotlin.test.assertEquals
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

    @Test
    fun `observableStateful calls callback after the state changed`() {
        var actualOldValue = ""
        var actualNewValue = ""
        val stateHolder = object : StateHolder by StateHolder() {
            var title by observableStateful("An empty") { oldValue, newValue ->
                actualOldValue = oldValue
                actualNewValue = newValue
            }
        }
        stateHolder.title = "The title"
        assertEquals(
            expected = "An empty",
            actual = actualOldValue
        )
        assertEquals(
            expected = "The title",
            actual = actualNewValue
        )
    }
}