package com.dedany.cinenear.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.cinenear.data.Movie
import com.dedany.cinenear.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FavoriteClick : DetailAction
    data object MessageShown : DetailAction
}


class DetailViewModel(
    id: Int,
    private val repository: MoviesRepository
) : ViewModel() {

   val state : StateFlow<UiState> = repository.findMovieById(id)
       .map { UiState(movie = it) }
       .stateIn(
           scope = viewModelScope,
           started = SharingStarted.WhileSubscribed(5000),
           initialValue = UiState(loading = true)
       )

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )


    fun onFavoriteClicked() {
        state.value.movie?.let {
            viewModelScope.launch {
                repository.toggleFavorite(it)
            }
        }
    }
}
