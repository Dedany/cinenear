package com.dedany.cinenear.ui.screens.detail

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.dedany.cinenear.sampleMovie
import com.dedany.cinenear.ui.common.Result
import com.dedany.cinenear.ui.screens.detail.DetailScreen
import com.dedany.cinenear.ui.screens.detail.LOADING_INDICATOR_TAG
import org.junit.Assert.assertTrue
import com.dedany.cinenear.R
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            DetailScreenContent(
                state = Result.Loading,
                providersState = Result.Loading,
                onBack = {},
                onFavoriteClicked = {}
            )
        }

        onNodeWithTag(LOADING_INDICATOR_TAG).assertExists()
    }

    @Test
    fun whenErrorState_showError(): Unit = with(composeTestRule) {
        setContent {
            DetailScreenContent(
                state = Result.Error(RuntimeException("An error occurred")),
                providersState = Result.Loading,
                onBack = {},
                onFavoriteClicked = {}
            )
        }

        onNodeWithText("An error occurred").assertExists()
    }

    @Test
    fun whenSuccessState_movieIsShown(): Unit = with(composeTestRule) {
        setContent {
            DetailScreenContent (
                state = Result.Success(sampleMovie(2)),
                providersState = Result.Loading,
                onBack = {},
                onFavoriteClicked = {}
            )
        }

        onNodeWithText("Title 2").assertExists()
    }

    @Test
    fun whenFavoriteClicked_listenerIsCalled(): Unit = with(composeTestRule) {
        var clicked = false
        setContent {
            DetailScreenContent (
                state = Result.Success(sampleMovie(2)),
                providersState = Result.Loading,
                onBack = {},
                onFavoriteClicked = { clicked = true }
            )
        }

        onNodeWithContentDescription(getStringResource(R.string.favorite)).performClick()
        assertTrue(clicked)
    }

    @Test
    fun whenBackClicked_listenerIsCalled(): Unit = with(composeTestRule) {
        var clicked = false
        setContent {
            DetailScreenContent(
                state = Result.Success(sampleMovie(2)),
                providersState = Result.Loading,
                onBack = { clicked = true },
                onFavoriteClicked = {}
            )
        }

        onNodeWithContentDescription(getStringResource(R.string.back)).performClick()
        assertTrue(clicked)
    }

}

private fun getStringResource(@StringRes id: Int): String {
    val ctx = InstrumentationRegistry.getInstrumentation().targetContext
    return ctx.getString(id)
}