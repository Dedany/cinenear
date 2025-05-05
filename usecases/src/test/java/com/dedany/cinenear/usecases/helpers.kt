package com.dedany.cinenear.usecases
import com.dedany.cinenear.domain.entities.Movie


fun sampleMovie(id: Int) = Movie(
    id = id,
    title = "Title",
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