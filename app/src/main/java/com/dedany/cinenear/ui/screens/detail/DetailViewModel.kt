package com.dedany.cinenear.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.cinenear.domain.Movie
import com.dedany.cinenear.usecases.FindMovieByIdUseCase
import com.dedany.cinenear.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DetailAction {
    data object FavoriteClick : DetailAction
    data object MessageShown : DetailAction
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    id: Int,
    findMovieByIdUseCase: FindMovieByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

   val state : StateFlow<UiState> = findMovieByIdUseCase(id)
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
                toggleFavoriteUseCase(it)
            }
        }
    }
}
