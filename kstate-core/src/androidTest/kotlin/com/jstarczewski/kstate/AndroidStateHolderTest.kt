package com.jstarczewski.kstate

import androidx.compose.runtime.snapshots.Snapshot
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AndroidStateHolderTest {

    @Test
    fun `initial isLoading state is equal initial value of false`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        assertFalse(stateHolder.isLoading)
    }

    @Test
    fun `isLoading state does not to true change withing snapshot if it was not applied`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        val snapshot = Snapshot.takeMutableSnapshot()
        assertFalse(stateHolder.isLoading)
        snapshot.enter {
            stateHolder.isLoading = true
        }
        assertFalse(stateHolder.isLoading)
    }

    @Test
    fun `isLoading state changes to true within a snapshot after applying`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        val snapshot = Snapshot.takeMutableSnapshot()
        assertFalse(stateHolder.isLoading)
        snapshot.enter {
            stateHolder.isLoading = true
        }
        snapshot.apply()
        assertTrue(stateHolder.isLoading)
    }

    @Test
    fun `isLoading state changes outside of snapshot to true`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        stateHolder.isLoading = true
        assertTrue { stateHolder.isLoading }
    }

    @Test
    fun `isLoading state changes for second snapshot when it is taken after the first one`() {
        val stateHolder = object : StateHolder by StateHolder() {
            var isLoading by state(false)
        }
        val snapshot = Snapshot.takeMutableSnapshot()
        snapshot.enter {
            assertFalse(stateHolder.isLoading)
        }
        stateHolder.isLoading = true
        val secondFrameSnapshot = Snapshot.takeSnapshot()
        secondFrameSnapshot.enter {
            assertTrue(stateHolder.isLoading)
        }
    }
}