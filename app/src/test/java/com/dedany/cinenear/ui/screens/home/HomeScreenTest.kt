package com.dedany.cinenear.ui.screens.home



import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.sampleMovies
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test


class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgressIndicator() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading = true),
                onMovieClick = {}
            )
        }

        // Usa un testTag para encontrar el CircularProgressIndicator
        composeTestRule.onNodeWithTag("LOADING_INDICATOR").assertExists()
    }

    @Test
    fun whenMoviesArePresent_displayThem() {
        val movies = sampleMovies(1, 2, 3)

        composeTestRule.setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading = false, movies = movies),
                onMovieClick = {}
            )
        }

        composeTestRule.onNodeWithText("Title 2").assertExists()
    }

    @Test
    fun whenMovieClicked_callbackIsInvoked() {
        val movies = sampleMovies(1, 2, 3)
        var selectedMovie: Movie? = null

        composeTestRule.setContent {
            HomeScreen(
                state = HomeViewModel.UiState(loading = false, movies = movies),
                onMovieClick = { selectedMovie = it }
            )
        }

        composeTestRule.onNodeWithText("Title 2").performClick()

        assertEquals(2, selectedMovie?.id)
    }
}
