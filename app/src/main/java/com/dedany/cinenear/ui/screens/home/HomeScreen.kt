package com.dedany.cinenear.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.domain.model.PlatformFilter

@Composable
fun HomeScreen(
    state: HomeViewModel.UiState,
    onFilterSelected: (PlatformFilter) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // --- Filtros arriba ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            PlatformFilter.values().forEach { filter ->
                FilterChip(
                    selected = filter == state.selectedFilter,
                    onClick = { println("filtro seleccionado: $filter")
                        onFilterSelected(filter) },
                    label = { Text(filter.name.lowercase().replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Loading ---
        if (state.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("LOADING_INDICATOR"))
            }
        } else {
            // --- Grid de películas ---
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.movies, key = { it.movie.id }) { movieWithProviders ->
                    MovieItem(
                        movie = movieWithProviders.movie,
                        providers = movieWithProviders.providers,
                        onClick = { onMovieClick(movieWithProviders.movie.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    providers: Providers?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        // --- Imagen del póster ---
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster ?: ""}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f), // mantiene proporción de póster
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        // --- Título de la película ---
        Text(text = movie.title)
    }
}
