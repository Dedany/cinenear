package com.dedany.cinenear.data.datasource

import com.dedany.cinenear.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    val movies: Flow<List<Movie>>
    fun findMovieById(id: Int): Flow<Movie?>
    suspend fun save(movies: List<Movie>)
}

