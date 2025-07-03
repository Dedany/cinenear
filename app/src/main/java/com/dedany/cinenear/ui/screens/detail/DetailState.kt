package com.dedany.cinenear.ui.screens.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.ui.common.Result


@OptIn(ExperimentalMaterial3Api::class)
class DetailState(
    private val state: Result<Movie>,
    private val providersState: Result<Providers>,
    val scrollBehavior: TopAppBarScrollBehavior,
    val snackbarHostState: SnackbarHostState
) {
    val movie: Movie?
        get() = (state as? Result.Success)?.data

    val providers: Providers?
    get() = (providersState as? Result.Success)?.data

    val topBarTitle: String
        get() = movie?.title ?: ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDetailState(
    state: Result<Movie>,
    providersState: Result<Providers>,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
)= remember(state,providersState) { DetailState(state,providersState, scrollBehavior,snackbarHostState) }
