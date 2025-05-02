package com.dedany.cinenear.domain.entities

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val poster: String,
    val backdrop: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: String,
    val voteAverage: String,
    val isFavorite: Boolean
)

