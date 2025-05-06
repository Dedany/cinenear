package com.dedany.cinenear.ui.screens.home

import app.cash.turbine.test
import com.dedany.cinenear.data.buildMoviesRepositoryWith
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.sampleMovies
import com.dedany.cinenear.testrules.CoroutinesTestRule
import com.dedany.cinenear.ui.common.Result
import com.dedany.cinenear.usecases.FetchMoviesUseCase
import junit.framework.TestCase.assertEquals
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

        vm.onUiReady()

        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = true), awaitItem())
            assertEquals(HomeViewModel.UiState(loading = false, movies = emptyList()), awaitItem())
            assertEquals(HomeViewModel.UiState(loading = false, movies = remoteData), awaitItem())
        }

    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = sampleMovies(1, 2)
        val vm = buildViewModelWith(localData = localData)

        vm.onUiReady()

        vm.state.test {
            assertEquals(HomeViewModel.UiState(loading = true), awaitItem())
            assertEquals(HomeViewModel.UiState(loading = false, movies = localData), awaitItem())
        }


    }

}

private fun buildViewModelWith(
    localData: List<Movie> = emptyList(),
    remoteData: List<Movie> = emptyList()
): HomeViewModel {
    val fetchMoviesUseCase = FetchMoviesUseCase(buildMoviesRepositoryWith(localData, remoteData))
    return HomeViewModel(fetchMoviesUseCase)
}