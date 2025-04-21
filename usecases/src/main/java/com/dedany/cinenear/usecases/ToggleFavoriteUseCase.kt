package com.dedany.cinenear.usecases

import com.dedany.cinenear.domain.Movie
import com.dedany.cinenear.data.MoviesRepository

class ToggleFavoriteUseCase(
    private val moviesRepository: MoviesRepository
) {
    operator suspend fun invoke(movie: Movie) {
        moviesRepository.toggleFavorite(movie)
    }
}