package com.dedany.cinenear.data

import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.data.datasource.MoviesLocalDataSource
import com.dedany.cinenear.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) {

    val movies: Flow<List<Movie>> = localDataSource.movies.onEach { localMovies ->
        if(localMovies.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val remoteMovies = remoteDataSource.fetchPopularMovies(region)
            localDataSource.save(remoteMovies)
    }
    }

   fun findMovieById(id: Int): Flow<Movie?> = localDataSource.findMovieById(id).onEach {
       if(it ==null){
            val remoteMovie = remoteDataSource.findMovieById(id)
            localDataSource.save(listOf(remoteMovie))
        }
        }

    suspend fun toggleFavorite(movie: Movie) {
        localDataSource.save(listOf(movie.copy(favorite = !movie.favorite)))
    }
}











