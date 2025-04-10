package com.dedany.cinenear.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.cinenear.data.Movie
import com.dedany.cinenear.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FavoriteClick : DetailAction
    data object MessageShown : DetailAction
}


class DetailViewModel(
    private val id: Int,
    private val repository: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    var state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
        val message: String? = null
    )

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.findMovieById(id).collect { movie ->
                _state.value = UiState(loading = false, movie = movie)
            }
        }
    }

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClick -> onFavoriteClicked()
            is DetailAction.MessageShown -> onMessageShown()
        }
    }


    private fun onFavoriteClicked() {
        _state.update { it.copy(message = "Favorite clicked") }
    }

    private fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }
}
