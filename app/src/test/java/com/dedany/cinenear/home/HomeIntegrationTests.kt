package com.dedany.cinenear.home

import com.dedany.cinenear.sampleMovies
import com.dedany.cinenear.testrules.CoroutinesTestRule
import com.dedany.cinenear.ui.screens.home.HomeViewModel
import junit.framework.TestCase
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
            TestCase.assertEquals(HomeViewModel.UiState(loading = true), awaitItem())
            TestCase.assertEquals(
                HomeViewModel.UiState(loading = false, movies = emptyList()),
                awaitItem()
            )
            TestCase.assertEquals(
                HomeViewModel.UiState(loading = false, movies = remoteData),
                awaitItem()
            )
        }

    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = sampleMovies(1, 2)
        val vm = buildViewModelWith(localData = localData)

        vm.onUiReady()

        vm.state.test {
            TestCase.assertEquals(HomeViewModel.UiState(loading = true), awaitItem())
            TestCase.assertEquals(
                HomeViewModel.UiState(loading = false, movies = localData),
                awaitItem()
            )
        }


    }

}