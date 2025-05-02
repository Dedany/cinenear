package com.dedany.cinenear.usecases

import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.data.MoviesRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.toggleFavorite(movie)
    }
}