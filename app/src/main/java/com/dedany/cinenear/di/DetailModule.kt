package com.dedany.cinenear.di

import androidx.lifecycle.SavedStateHandle
import com.dedany.cinenear.ui.navigation.NavArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.scopes.ViewScoped
import javax.inject.Named
import javax.inject.Qualifier


@Module
@InstallIn(ViewModelComponent::class)
class DetailModule {

    @Provides
    @ViewModelScoped
    @Named("MovieId")
    fun provideMovieId(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle[NavArgs.MovieId.key]
            ?: throw IllegalStateException("MovieId not found in the state handle")
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MovieId