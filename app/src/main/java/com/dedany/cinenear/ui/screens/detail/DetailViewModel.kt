package com.dedany.cinenear.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.cinenear.ui.common.Result
import com.dedany.cinenear.di.MovieId
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.ui.common.ifSuccess
import com.dedany.cinenear.ui.common.stateAsResultIn
import com.dedany.cinenear.usecases.FetchProvidersUseCase
import com.dedany.cinenear.usecases.FindMovieByIdUseCase
import com.dedany.cinenear.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @MovieId id: Int,
    findMovieByIdUseCase: FindMovieByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val fetchProvidersUseCase: FetchProvidersUseCase
) : ViewModel() {

    val state: StateFlow<Result<Movie>> =
        findMovieByIdUseCase(id).stateAsResultIn(scope = viewModelScope)

    private val _providersState = MutableStateFlow<Result<Providers>>(Result.Loading)
    val providersState: StateFlow<Result<Providers>> = _providersState

    init {
        loadProviders(id)
    }

    fun onFavoriteClicked() {
        state.value.ifSuccess {
            viewModelScope.launch {
                toggleFavoriteUseCase(it)
            }
        }
    }

            private fun loadProviders(movieId: Int) {
                viewModelScope.launch {
                    try {
                        val providers = fetchProvidersUseCase(movieId)
                        println("Providers cargados: $providers")
                        _providersState.value = Result.Success(providers)
                    } catch (e: Exception) {
                        println("Error al cargar los proveedores: ${e.message}")
                        _providersState.value = Result.Error(e)
                    }
                }
            }
        }

