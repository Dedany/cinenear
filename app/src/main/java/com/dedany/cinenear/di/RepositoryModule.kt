package com.dedany.cinenear.di

import com.dedany.cinenear.data.MoviesRepository
import com.dedany.cinenear.data.RegionRepository
import com.dedany.cinenear.data.datasource.MoviesLocalDataSource
import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMoviesRepository(
        regionRepository: RegionRepository,
        localDataSource: MoviesLocalDataSource,
        remoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository {
        return MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    }
}