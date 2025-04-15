package com.dedany.cinenear.usecases

import com.dedany.cinenear.data.Movie
import com.dedany.cinenear.data.MoviesRepository
import kotlinx.coroutines.flow.Flow

class FetchMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<List<Movie>> = moviesRepository.movies
}