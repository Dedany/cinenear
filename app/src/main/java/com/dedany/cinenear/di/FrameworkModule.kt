package com.dedany.cinenear.di

import android.app.Application
import androidx.room.Room
import com.dedany.cinenear.framework.database.MoviesDataBase
import com.dedany.cinenear.framework.remote.MoviesClient
import com.dedany.cinenear.framework.remote.MoviesService
import com.dedany.cinenear.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FrameworkModule {


    //Hilt proporciona la base de datos
    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MoviesDataBase::class.java,
        "movies-db"
    )
        .build()

    @Provides
    fun providesMoviesDao(db: MoviesDataBase) = db.moviesDao()


    //Hilt proporciona las películas
    @Provides
    @Singleton
    fun provideMoviesService(
        @Named("apiKey") apiKey: String,
        @Named("apiUrl") apiUrl: String
    ): MoviesService = MoviesClient(apiKey, apiUrl).instance

    @Provides
    @Singleton
    fun provideApp(application: Application): App {
        return application as App
    }
}