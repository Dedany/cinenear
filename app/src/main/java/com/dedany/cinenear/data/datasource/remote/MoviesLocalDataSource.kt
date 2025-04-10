package com.dedany.cinenear.data.datasource.remote

import com.dedany.cinenear.data.Movie
import com.dedany.cinenear.data.datasource.database.MoviesDao

class MoviesLocalDataSource(private val moviesDao: MoviesDao) {

    val movies = moviesDao.fetchPopularMovies()

    fun findMovieById(id: Int) = moviesDao.findMovieById(id)

    suspend fun isEmpty() = moviesDao.countMovies() == 0

    suspend fun saveMovies(movies: List<Movie>) = moviesDao.saveMovies(movies)
}