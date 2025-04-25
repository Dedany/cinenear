package com.dedany.cinenear.di

import com.dedany.cinenear.data.datasource.MoviesLocalDataSource
import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.framework.MoviesLocalDataSourceImpl
import com.dedany.cinenear.framework.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    abstract fun bindMoviesRemoteDataSource(remoteDataSource: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource
    @Binds
    abstract fun bindMoviesLocalDataSource(localDataSource: MoviesLocalDataSourceImpl): MoviesLocalDataSource

}
