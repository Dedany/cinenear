package com.dedany.cinenear.ui.screens.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dedany.cinenear.domain.model.PlatformFilter
import com.dedany.cinenear.sampleMoviesWithProviders
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgressIndicator() {
        val loadingState = HomeViewModel.UiState(loading = true)
        composeTestRule.setContent {
            HomeScreen(
                state = loadingState,
                onFilterSelected = {},
                onMovieClick = {}
            )
        }

        composeTestRule.onNodeWithTag("LOADING_INDICATOR").assertExists()
    }

    @Test
    fun whenMoviesArePresent_displayThem() {
        val movies = sampleMoviesWithProviders(1, 2, 3)

        composeTestRule.setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading = false, movies = movies),
                onFilterSelected = {},
                onMovieClick = {}
            )
        }

        composeTestRule.onNodeWithText("Title 2").assertExists()
    }

    @Test
    fun whenMovieClicked_callbackIsInvoked() {
        val movies = sampleMoviesWithProviders(1, 2, 3)
        var selectedMovieId: Int? = null

        composeTestRule.setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading = false, movies = movies),
                onFilterSelected = {},
                onMovieClick = { selectedMovieId = it }
            )
        }

        composeTestRule.onNodeWithText("Title 2").performClick()

        assertEquals(2, selectedMovieId)
    }

    @Test
    fun whenFilterSelected_callbackIsInvoked() {
        var selectedFilter: PlatformFilter? = null

        composeTestRule.setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading = false),
                onFilterSelected = { selectedFilter = it },
                onMovieClick = {}
            )
        }

        composeTestRule.onNodeWithText("Rent").performClick()

        assertEquals(PlatformFilter.RENT, selectedFilter)
    }
}
