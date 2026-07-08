package com.dedany.cinenear.ui.screens.home

import app.cash.turbine.test
import com.dedany.cinenear.data.buildMoviesRepositoryWith
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.sampleMovies
import com.dedany.cinenear.testrules.CoroutinesTestRule
import com.dedany.cinenear.usecases.FetchMoviesWithProvidersUseCase
import com.dedany.cinenear.usecases.FetchWatchProvidersUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HomeIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = sampleMovies(1, 2)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = true), awaitItem())
            vm.onUiReady()

            var loadedState = awaitItem()
            if (loadedState.movies.isEmpty()) {
                loadedState = awaitItem()
            }

            assertTrue(!loadedState.loading)
            assertEquals(listOf(1, 2), loadedState.movies.map { it.movie.id })

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = sampleMovies(1, 2)
        val vm = buildViewModelWith(localData = localData)

        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = true), awaitItem())
            vm.onUiReady()

            val loadedState = awaitItem()
            assertTrue(!loadedState.loading)
            assertEquals(listOf(1, 2), loadedState.movies.map { it.movie.id })

            cancelAndIgnoreRemainingEvents()
        }
    }
}

private fun buildViewModelWith(
    localData: List<Movie> = emptyList(),
    remoteData: List<Movie> = emptyList()
): HomeViewModel {
    val repository = buildMoviesRepositoryWith(localData, remoteData)
    return HomeViewModel(
        fetchMoviesWithProvidersUseCase = FetchMoviesWithProvidersUseCase(repository),
        fetchWatchProvidersUseCase = FetchWatchProvidersUseCase(repository)
    )
}
