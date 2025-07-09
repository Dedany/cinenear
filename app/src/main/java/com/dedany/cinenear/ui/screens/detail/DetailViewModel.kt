package com.dedany.cinenear.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.ui.common.Result
import com.dedany.cinenear.ui.common.stateAsResultIn
import com.dedany.cinenear.usecases.FetchProvidersUseCase
import com.dedany.cinenear.usecases.FindMovieByIdUseCase
import com.dedany.cinenear.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val findMovieByIdUseCase: FindMovieByIdUseCase,
    private val fetchProvidersUseCase: FetchProvidersUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _movieId = MutableStateFlow<Int?>(null)

    fun loadMovie(id: Int) {
        _movieId.value = id
    }

    val state: StateFlow<Result<Movie>> = _movieId
        .filterNotNull()
        .flatMapLatest { id -> findMovieByIdUseCase(id) }
        .stateAsResultIn(viewModelScope)

    val providersState: StateFlow<Result<Providers>> = _movieId
        .filterNotNull()
        .flatMapLatest { id -> fetchProvidersUseCase(id) }
        .stateAsResultIn(viewModelScope)

    fun onFavoriteClicked() {
        val current = state.value
        if (current is Result.Success) {
            viewModelScope.launch {
                toggleFavoriteUseCase(current.data)
            }
        }
    }
}
