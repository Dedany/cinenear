package com.dedany.cinenear.data

import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.data.datasource.MoviesLocalDataSource
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) {

    val movies: Flow<List<Movie>>
        get() = localDataSource.movies.onEach { localMovies ->
        if (localMovies.isEmpty()) {
            val remoteMovies = remoteDataSource.fetchPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(remoteMovies)
        }
    }

    fun findMovieById(id: Int): Flow<Movie> = localDataSource.findMovieById(id)
        .onEach { movie ->
            if (movie == null) {
                val remoteMovie = remoteDataSource.findMovieById(id)
                localDataSource.save(listOf(remoteMovie))
            }
        }
        .filterNotNull()

    suspend fun toggleFavorite(movie: Movie) {
        localDataSource.save(listOf(movie.copy(isFavorite = !movie.isFavorite)))
    }

    suspend fun fetchWatchProviders(movieId: Int): Providers {
        return remoteDataSource.fetchWatchProviders(movieId)
    }
}











