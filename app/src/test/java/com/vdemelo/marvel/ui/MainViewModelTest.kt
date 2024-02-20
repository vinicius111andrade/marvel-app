package com.vdemelo.marvel.ui

import MainDispatcherRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.clearAllMocks
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `When updateInternetStatus() Called Should Update LiveData`() {
        viewModel = MainViewModel()
        val hasInternet = true
        viewModel.updateInternetStatus(isConnected = hasInternet)
        assertEquals(hasInternet, viewModel.connectedToTheInternet.value)
        assertEquals(false, viewModel.triggerCheckConnection.value)
    }
}
