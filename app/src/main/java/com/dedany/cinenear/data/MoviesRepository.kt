package com.dedany.cinenear.data

import android.annotation.SuppressLint

class MoviesRepository {

    suspend fun fetchPopularMovies(region: String): List<Movie> =
        MoviesClient
            .instance
            .fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    suspend fun findMovieById(id: Int): Movie =
        MoviesClient
            .instance
            .fetchMovieById(id)
            .toDomainModel()
}

@SuppressLint("DefaultLocale")
private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        poster = "https://image.tmdb.org/t/p/w185/$posterPath",
        backdrop = if (backdropPath != null) "https://image.tmdb.org/t/p/w780/$backdropPath" else null,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = String.format("%.1f", popularity.toFloat()),
        voteAverage = String.format("%.1f", voteAverage.toFloat())
    )





