package com.dedany.cinenear.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.cinenear.data.Movie
import com.dedany.cinenear.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository : MoviesRepository
): ViewModel() {

   private val _state = MutableStateFlow(UiState())
    val state  = _state

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.movies.collect{movies->
                _state.value = UiState(loading = false, movies = movies)
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )

}