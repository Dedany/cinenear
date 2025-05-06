package com.dedany.cinenear.ui.screens.home

import app.cash.turbine.test
import com.dedany.cinenear.sampleMovies
import com.dedany.cinenear.testrules.CoroutinesTestRule
import com.dedany.cinenear.usecases.FetchMoviesUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
    @RunWith(MockitoJUnitRunner::class)
    class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var fetchMoviesUseCase: FetchMoviesUseCase

    private lateinit var vm: HomeViewModel

    @Before
    fun setUp() {
        vm = HomeViewModel(fetchMoviesUseCase)
    }

    @Test
    fun `Movies are not requested if UI is not ready`() = runTest {
        vm.state.first()
        runCurrent()

        verify(fetchMoviesUseCase, times(0)).invoke()
    }


    @Test
    fun `Movies are requested if UI is ready`() = runTest {
        val movies = sampleMovies(1, 2, 3)
        whenever(fetchMoviesUseCase()).thenReturn(flowOf(movies))

        vm.onUiReady()

        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = true, movies = emptyList()), awaitItem())
            assertEquals(HomeViewModel.UiState(loading = false, movies = movies), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Flow is cancelled when exception is thrown`() = runTest {
        val error = RuntimeException("Boom!")
        whenever(fetchMoviesUseCase()).thenThrow(error)

        vm.onUiReady()

        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = true), awaitItem())
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }
}

