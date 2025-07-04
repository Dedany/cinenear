package com.dedany.cinenear.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.dedany.cinenear.R
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.domain.entities.Provider
import com.dedany.cinenear.ui.common.AcScaffold
import com.dedany.cinenear.ui.theme.Screen
import com.dedany.cinenear.ui.common.Result

const val LOADING_INDICATOR_TAG = "LOADING_INDICATOR"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(vm: DetailViewModel = hiltViewModel(), onBack: () -> Unit) {
    val state by vm.state.collectAsState()
    val providersState by vm.providersState.collectAsState()

    DetailScreen(
        state = state,
        providersState = providersState,
        onBack = onBack,
        onFavoriteClicked = vm::onFavoriteClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: Result<Movie>,
    providersState: Result<Providers>,
    onBack: () -> Unit,
    onFavoriteClicked: () -> Unit
) {
    val detailState = rememberDetailState(state, providersState)

    Screen {
        AcScaffold(
            state = state,
            topBar = {
                DetailTopBar(
                    title = detailState.topBarTitle ?: "",
                    scrollBehavior = detailState.scrollBehavior,
                    onBack = onBack
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onFavoriteClicked) {
                    val favorite = detailState.movie?.isFavorite ?: false
                    Icon(
                        imageVector = if (favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.favorite),
                        tint = if (favorite) Color.Red else Color.Blue
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = detailState.snackbarHostState) },
            modifier = Modifier.nestedScroll(detailState.scrollBehavior.nestedScrollConnection)
        ) { padding, movie ->
            if (state is Result.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag(LOADING_INDICATOR_TAG)
                )
            } else {
                MovieDetail(
                    movie = movie,
                    providers = detailState.providers,
                    modifier = Modifier.padding(padding)
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
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun MovieDetail(
    movie: Movie,
    providers: Providers?,
    modifier: Modifier = Modifier
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
            text = movie.overview,
            modifier = Modifier.padding(16.dp)
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
        ProvidersSection(providers = providers)
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

@Composable
private fun ProvidersSection(providers: Providers?, modifier: Modifier = Modifier) {
    if (providers == null) {
        Text("Cargando proveedores...")
        return
    }

    if (providers.buy.isNullOrEmpty() && providers.rent.isNullOrEmpty() && providers.flatrate.isNullOrEmpty()) {
        Text("No disponible en plataformas.")
        return
    }

    Column(modifier = modifier.padding(16.dp)) {
        if (!providers.flatrate.isNullOrEmpty()) {
            Text(
                text = "Disponible en plataformas de suscripción:",
                style = MaterialTheme.typography.titleMedium
            )
            ProvidersRow(providers.flatrate)
        }

        if (!providers.rent.isNullOrEmpty()) {
            Text(
                text = "Disponible para alquilar:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            ProvidersRow(providers.rent)
        }

        if (!providers.buy.isNullOrEmpty()) {
            Text(
                text = "Disponible para comprar:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            ProvidersRow(providers.buy)
        }
    }
}

@Composable
private fun ProvidersRow(providersList: List<Provider>) {
    Row {
        providersList.forEach { provider ->
            Column(modifier = Modifier.padding(4.dp)) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w45${provider.logoPath}",
                    contentDescription = provider.name,
                    modifier = Modifier.padding(4.dp)
                )
                Text(text = provider.name, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
