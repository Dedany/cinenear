package com.dedany.cinenear.data

import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val remoteDataSource: MoviesRemoteDataSource
) {
    suspend fun fetchPopularMovies(): List<Movie> = remoteDataSource.fetchPopularMovies(
        regionRepository.findLastRegion()
    )

    suspend fun findMovieById(id: Int): Movie =
        remoteDataSource.findMovieById(id)
}











