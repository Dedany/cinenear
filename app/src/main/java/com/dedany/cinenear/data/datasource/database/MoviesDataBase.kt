package com.dedany.cinenear.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dedany.cinenear.domain.Movie


@Database(entities = [DbMovie::class], version = 1, exportSchema = false)
abstract class MoviesDataBase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}