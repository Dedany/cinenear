package com.dedany.cinenear.usecases

import com.dedany.cinenear.data.MoviesRepository
import com.dedany.cinenear.domain.model.MovieWithProviders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchMoviesWithProvidersUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<List<MovieWithProviders>> =
        repository.movies.map { movies ->
            movies.map { MovieWithProviders(movie = it, providers = null) }
        }
}
