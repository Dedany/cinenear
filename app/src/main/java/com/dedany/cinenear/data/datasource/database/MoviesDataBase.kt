package com.dedany.cinenear.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dedany.cinenear.data.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDataBase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}