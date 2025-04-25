package com.dedany.cinenear.di
/*
import android.app.Application
import androidx.room.Room
import com.dedany.cinenear.framework.database.MoviesDataBase
import com.dedany.cinenear.framework.remote.MoviesClient
import com.dedany.cinenear.framework.remote.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FrameworkModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MoviesDataBase::class.java,
        "movies-db")
        .build()

    @Provides
    fun providesMoviesDao(db: MoviesDataBase) = db.moviesDao()

    @Provides
    @Singleton
    fun provideMoviesService (@Named("apiKey") apiKey: String) : MoviesService = MoviesClient(apiKey).instance
}  */