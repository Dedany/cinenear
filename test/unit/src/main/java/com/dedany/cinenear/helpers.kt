package com.dedany.cinenear

import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.model.MovieWithProviders


fun sampleMovie(id: Int) = Movie(
    id = id,
    title = "Title $id",
    overview = "Overview",
    releaseDate = "01/01/2025",
    poster = "",
    backdrop = "",
    originalTitle = "EN",
    originalLanguage = "Title",
    popularity = "5",
    voteAverage = "5",
    isFavorite = false
)

fun sampleMovies(vararg ids: Int) = ids.map { sampleMovie(it) }

fun sampleMovieWithProviders(id: Int) = MovieWithProviders(
    movie = sampleMovie(id),
    providers = null
)

fun sampleMoviesWithProviders(vararg ids: Int) = ids.map { sampleMovieWithProviders(it) }