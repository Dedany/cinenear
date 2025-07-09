package com.dedany.cinenear.ui.screens.detail

import app.cash.turbine.test
import com.dedany.cinenear.data.buildMoviesRepositoryWith
import com.dedany.cinenear.sampleMovie
import com.dedany.cinenear.sampleMovies
import com.dedany.cinenear.testrules.CoroutinesTestRule
import com.dedany.cinenear.ui.common.Result
import com.dedany.cinenear.usecases.FetchProvidersUseCase
import com.dedany.cinenear.usecases.FetchWatchProvidersUseCase
import com.dedany.cinenear.usecases.FindMovieByIdUseCase
import com.dedany.cinenear.usecases.ToggleFavoriteUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        val moviesRepository = buildMoviesRepositoryWith(localData = sampleMovies(1, 2))
        vm = DetailViewModel(
            findMovieByIdUseCase = FindMovieByIdUseCase(moviesRepository),
            fetchProvidersUseCase = FetchProvidersUseCase(moviesRepository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(moviesRepository)
        )

        vm.loadMovie(2)
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(sampleMovie(2)), awaitItem())
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(sampleMovie(2)), awaitItem())

            vm.onFavoriteClicked()
            runCurrent()

            assertEquals(Result.Success(sampleMovie(2).copy(isFavorite = true)), awaitItem())
        }
    }
}
