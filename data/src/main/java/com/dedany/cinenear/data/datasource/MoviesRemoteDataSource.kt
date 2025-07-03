package com.dedany.cinenear.data.datasource

import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers


interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(region: String): List<Movie>

    suspend fun findMovieById(id: Int): Movie

    suspend fun fetchWatchProviders(id: Int): Providers
}

