package com.dedany.cinenear.ui.screens.detail

import app.cash.turbine.test
import com.dedany.cinenear.sampleMovie
import com.dedany.cinenear.testrules.CoroutinesTestRule
import com.dedany.cinenear.ui.common.Result
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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findMovieByIdUseCase: FindMovieByIdUseCase

    @Mock
    lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private lateinit var vm: DetailViewModel

    private val movie = sampleMovie(2)

    @Before
    fun setUp() {
        whenever(findMovieByIdUseCase(2)).thenReturn(flowOf(movie))
        vm = DetailViewModel(2, findMovieByIdUseCase, toggleFavoriteUseCase)
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
    fun `Favorite action calls the corresponding use case`() =
        runTest(coroutinesTestRule.testDispatcher) {
            vm.state.test {
                assertEquals(Result.Loading, awaitItem())
                assertEquals(Result.Success(movie), awaitItem())

                vm.onFavoriteClicked()
                runCurrent()

                verify(toggleFavoriteUseCase).invoke(any())
            }
        }
}
