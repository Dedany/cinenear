package com.dedany.cinenear

import android.app.Application
import androidx.room.Room
import com.dedany.cinenear.framework.database.MoviesDataBase


class App: Application() {

    lateinit var db : MoviesDataBase
    private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            MoviesDataBase::class.java, "movies-db"
        ).build()

    }
}