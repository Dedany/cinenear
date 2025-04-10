package com.dedany.cinenear.data.datasource.remote

import com.dedany.cinenear.data.Movie
import com.dedany.cinenear.data.datasource.database.MoviesDao
import kotlinx.coroutines.flow.Flow

class MoviesLocalDataSource(private val moviesDao: MoviesDao) {

    val movies : Flow<List<Movie>> = moviesDao.fetchPopularMovies()

    fun findMovieById(id: Int) = moviesDao.findMovieById(id)

    suspend fun save(movies: List<Movie>) = moviesDao.saveMovies(movies)
}