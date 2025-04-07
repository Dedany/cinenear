package com.dedany.cinenear.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.AnnotatedString.*
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.dedany.cinenear.R
import com.dedany.cinenear.data.Movie
import com.dedany.cinenear.ui.common.LoadingProgressIndicator
import com.dedany.cinenear.ui.theme.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(vm: DetailViewModel, onBack: () -> Unit) {

    val detailState = rememberDetailState()
    val state by vm.state.collectAsState()

    detailState.ShowMessageEffect(message = state.message) {
        vm.onMessageShown()
    }
    Screen {
        Scaffold(
            topBar = {
                DetailTopBar(
                    title = state.movie?.title ?: "",
                    scrollBehavior = detailState.scrollBehavior,
                    onBack = onBack
                )
            },
            floatingActionButton = {
                FloatingActionButton({ vm.onFavoriteClicked() }) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = detailState.snackbarHostState) },
            modifier = Modifier.nestedScroll(detailState.scrollBehavior.nestedScrollConnection)
        ) { padding ->

            if (state.loading) {
                LoadingProgressIndicator(modifier = Modifier.padding(padding))
            }

            state.movie?.let { movie ->
                MovieDetail(
                    movie, modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailTopBar(
    title: String, scrollBehavior: TopAppBarScrollBehavior, onBack: () -> Unit
) {
    TopAppBar(title = { Text(text = title) }, navigationIcon = {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back)
            )
        }
    }, scrollBehavior = scrollBehavior
    )
}

@Composable
private fun MovieDetail(
    movie: Movie, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = movie.backdrop,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
        Text(
            text = movie.overview, modifier = Modifier.padding(16.dp)
        )
        Text(
            buildAnnotatedString {
                Property("Original title", movie.originalTitle)
                Property("Original language", movie.originalLanguage)
                Property("Release date", movie.releaseDate)
                Property("IMDB", movie.voteAverage.toString())
                Property("Popularity", movie.popularity.toString())
                Property("Vote count", movie.voteAverage.toString())
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
    }
}

@Composable
private fun AnnotatedString.Builder.Property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (!end) {
            append("\n")
        }
    }
}


