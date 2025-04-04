package com.dedany.cinenear.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.cinenear.data.Movie
import com.dedany.cinenear.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

   private val _state = MutableStateFlow(UiState())
    val state  = _state


    private val repository = MoviesRepository()


    fun onUiReady(region: String) {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, movies = repository.fetchPopularMovies(region))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )

}