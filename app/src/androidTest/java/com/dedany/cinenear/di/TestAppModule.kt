package com.dedany.cinenear.di

import android.app.Application
import androidx.room.Room
import com.dedany.cinenear.framework.database.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import javax.inject.Named
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FrameworkCoreExtrasModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MoviesDataBase {
        val db = Room.inMemoryDatabaseBuilder(
            app,
            MoviesDataBase::class.java
        )
            .setTransactionExecutor(Dispatchers.Main.asExecutor())
            .allowMainThreadQueries()
            .build()
        return db
    }

    @Provides
    @Singleton
    @Named("apiUrl")
    fun provideApiUrl(): String = "http://localhost:8080"
}
