package com.dedany.cinenear.ui.screens.detail

import app.cash.turbine.test
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.sampleMovie
import com.dedany.cinenear.testrules.CoroutinesTestRule
import com.dedany.cinenear.ui.common.Result
import com.dedany.cinenear.usecases.FetchProvidersUseCase
import com.dedany.cinenear.usecases.FindMovieByIdUseCase
import com.dedany.cinenear.usecases.ToggleFavoriteUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness

class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val mockitoRule = MockitoJUnit.rule().strictness(Strictness.LENIENT)

    @Mock
    lateinit var findMovieByIdUseCase: FindMovieByIdUseCase

    @Mock
    lateinit var fetchProvidersUseCase: FetchProvidersUseCase

    @Mock
    lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private lateinit var vm: DetailViewModel

    private val movie = sampleMovie(2)

    @Before
    fun setUp() {
        whenever(findMovieByIdUseCase(2)).thenReturn(flowOf(movie))
        whenever(fetchProvidersUseCase(2)).thenReturn(
            flowOf(Providers(link = "", buy = emptyList(), rent = emptyList(), flatrate = emptyList()))
        )
        vm = DetailViewModel(findMovieByIdUseCase, fetchProvidersUseCase, toggleFavoriteUseCase)
        vm.loadMovie(2)
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(movie), awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Favorite action calls the corresponding use case`() = runTest(coroutinesTestRule.testDispatcher) {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(movie), awaitItem())

            vm.onFavoriteClicked()
            runCurrent()

            verify(toggleFavoriteUseCase).invoke(any())
        }
    }
}
