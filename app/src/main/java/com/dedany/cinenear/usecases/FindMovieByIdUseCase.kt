package com.dedany.cinenear.usecases

import com.dedany.cinenear.domain.Movie
import com.dedany.cinenear.data.MoviesRepository
import kotlinx.coroutines.flow.Flow

class FindMovieByIdUseCase(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Movie?> = moviesRepository.findMovieById(id)
}