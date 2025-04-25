package com.dedany.cinenear.usecases

import com.dedany.cinenear.domain.Movie
import com.dedany.cinenear.data.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<List<Movie>> = moviesRepository.movies
}