package com.dedany.cinenear.data

import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.data.datasource.remote.MoviesLocalDataSource

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) {
    suspend fun fetchPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val movies = remoteDataSource.fetchPopularMovies(region)
            localDataSource.saveMovies(movies)
        }
        return localDataSource.fetchPopularMovies()
    }


    suspend fun findMovieById(id: Int): Movie {
        if (localDataSource.findMovieById(id) == null) {
            val movie = remoteDataSource.findMovieById(id)
            localDataSource.saveMovies(listOf(movie))
        }
        return checkNotNull(localDataSource.findMovieById(id))
    }
}











