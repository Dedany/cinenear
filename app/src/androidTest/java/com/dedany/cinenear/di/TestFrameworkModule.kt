package com.dedany.cinenear.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import androidx.room.Room
import com.dedany.cinenear.framework.database.MoviesDataBase
import com.dedany.cinenear.framework.remote.MoviesClient
import com.dedany.cinenear.framework.remote.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.Locale
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FrameworkModule::class]
)
object TestFrameworkModule {
    
    @Provides
    fun providesMoviesDao(db: MoviesDataBase) = db.moviesDao()
    
    @Provides
    @Singleton
    fun provideMoviesService(
        @Named("apiKey") apiKey: String,
        @Named("apiUrl") apiUrl: String
    ): MoviesService = MoviesClient(apiKey, apiUrl).instance
    
    @Provides
    @Singleton
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FrameworkModule.FrameworkCoreExtrasModule::class]
)
object TestFrameworkCoreExtrasModule {
    
    @Provides
    @Singleton
    fun provideDatabase(app: Application): MoviesDataBase {
        return Room.inMemoryDatabaseBuilder(
            app,
            MoviesDataBase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }
    
    @Provides
    @Singleton
    @Named("apiUrl")
    fun provideApiUrl(): String = "http://localhost:8080/"
} 