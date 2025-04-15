package com.dedany.cinenear.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [DbMovie::class], version = 1, exportSchema = false)
abstract class MoviesDataBase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}