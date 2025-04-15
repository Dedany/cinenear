package com.dedany.cinenear.data.datasource.remote

import com.dedany.cinenear.data.datasource.database.DbMovie
import com.dedany.cinenear.domain.Movie
import com.dedany.cinenear.data.datasource.database.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesLocalDataSource(private val moviesDao: MoviesDao) {

    val movies: Flow<List<Movie>> =
        moviesDao.fetchPopularMovies().map { movies -> movies.map { it.toDomainMovie() } }

    fun findMovieById(id: Int): Flow<Movie?> =
        moviesDao.findMovieById(id).map { it?.toDomainMovie() }

    suspend fun save(movies: List<Movie>) = moviesDao.saveMovies(movies.map { it.toDbMovie() })
}

private fun DbMovie.toDomainMovie(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    poster = poster,
    releaseDate = releaseDate,
    backdrop = backdrop,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    favorite = false

)

private fun Movie.toDbMovie() = DbMovie(
    id = id,
    title = title,
    overview = overview,
    poster = poster,
    releaseDate = releaseDate,
    backdrop = backdrop,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    favorite = false
)