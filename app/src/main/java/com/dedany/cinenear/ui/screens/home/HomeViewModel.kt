package com.dedany.cinenear.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.domain.model.PlatformFilter
import com.dedany.cinenear.domain.model.MovieWithProviders
import com.dedany.cinenear.usecases.FetchMoviesWithProvidersUseCase
import com.dedany.cinenear.usecases.FetchWatchProvidersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    fetchMoviesWithProvidersUseCase: FetchMoviesWithProvidersUseCase,
    private val fetchWatchProvidersUseCase: FetchWatchProvidersUseCase,
) : ViewModel() {

    private val _selectedFilter = MutableStateFlow(PlatformFilter.ALL)
    private val _uiReady = MutableStateFlow(false)
    private val _providersMap = MutableStateFlow<Map<Int, Providers>>(emptyMap()) // <- FALTABA ESTO

    // Flujo de películas
    private val allMoviesFlow = _uiReady
        .filter { it }
        .flatMapLatest { fetchMoviesWithProvidersUseCase() }

    // Combinamos las películas con los providers que vamos descargando
    private val moviesWithProviders: Flow<List<MovieWithProviders>> = combine(
        allMoviesFlow,
        _providersMap
    ) { movieList, providerMap ->
        movieList.map { movieWithProviders ->
            val providers = providerMap[movieWithProviders.movie.id]
            MovieWithProviders(movie = movieWithProviders.movie, providers = providers)
        }
    }

    // Combinamos con el filtro seleccionado
    val state: StateFlow<UiState> = combine(
        moviesWithProviders,
        _selectedFilter
    ) { movies, filter ->
        val filteredMovies = when (filter) {
            PlatformFilter.ALL -> movies
            PlatformFilter.FLATRATE -> movies.filter { it.providers?.flatrate?.isNotEmpty() == true }
            PlatformFilter.RENT -> movies.filter { it.providers?.rent?.isNotEmpty() == true }
            PlatformFilter.BUY -> movies.filter { it.providers?.buy?.isNotEmpty() == true }
        }

        UiState(
            loading = false,
            movies = filteredMovies,
            selectedFilter = filter
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = UiState(loading = true)
    )

    fun onUiReady() {
        _uiReady.value = true

        viewModelScope.launch {
            allMoviesFlow.firstOrNull()?.let { movieList ->
                fetchProvidersIfNeeded(movieList.map { it.movie })
            }
        }
    }

    fun onFilterSelected(filter: PlatformFilter) {
        println("ViewModel: Filtro cambiado a: $filter")
        _selectedFilter.value = filter
    }

    private fun fetchProvidersIfNeeded(movies: List<Movie>) {
        movies.forEach { movie ->
            if (_providersMap.value.containsKey(movie.id).not()) {
                viewModelScope.launch {
                    try {
                        val providers = fetchWatchProvidersUseCase(movie.id)
                        _providersMap.value = _providersMap.value + (movie.id to providers)
                    } catch (e: Exception) {
                        println("Error al cargar providers de ${movie.title}: ${e.message}")
                    }
                }
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<MovieWithProviders> = emptyList(),
        val selectedFilter: PlatformFilter = PlatformFilter.ALL
    )
}
