package com.dedany.cinenear.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbMovie(
    @PrimaryKey(autoGenerate = true)
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
    val favorite: Boolean
)

