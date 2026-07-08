package com.dedany.cinenear.ui.screens.home

import app.cash.turbine.test
import com.dedany.cinenear.sampleMoviesWithProviders
import com.dedany.cinenear.testrules.CoroutinesTestRule
import com.dedany.cinenear.usecases.FetchMoviesWithProvidersUseCase
import com.dedany.cinenear.usecases.FetchWatchProvidersUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
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
    lateinit var fetchMoviesWithProvidersUseCase: FetchMoviesWithProvidersUseCase

    @Mock
    lateinit var fetchWatchProvidersUseCase: FetchWatchProvidersUseCase

    private lateinit var vm: HomeViewModel

    @Before
    fun setUp() {
        vm = HomeViewModel(fetchMoviesWithProvidersUseCase, fetchWatchProvidersUseCase)
    }

    @Test
    fun `Movies are not requested if UI is not ready`() = runTest {
        vm.state.first()
        runCurrent()

        verify(fetchMoviesWithProvidersUseCase, times(0)).invoke()
    }

    @Test
    fun `Movies are requested if UI is ready`() = runTest {
        val movies = sampleMoviesWithProviders(1, 2, 3)
        whenever(fetchMoviesWithProvidersUseCase()).thenReturn(flowOf(movies))

        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = true, movies = emptyList()), awaitItem())
            vm.onUiReady()
            assertEquals(HomeViewModel.UiState(loading = false, movies = movies), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Loading state persists when no movies are emitted`() = runTest {
        whenever(fetchMoviesWithProvidersUseCase()).thenReturn(emptyFlow())

        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = true), awaitItem())
            vm.onUiReady()
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
