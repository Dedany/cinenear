package com.dedany.cinenear.di

import com.dedany.cinenear.data.datasource.LocationDataSource
import com.dedany.cinenear.data.datasource.RegionDataSource
import com.dedany.cinenear.framework.LocationDataSourceImpl
import com.dedany.cinenear.framework.RegionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocationAndRegionModule {

  @Binds
  abstract fun provideLocationDataSource(locationDataSource: LocationDataSourceImpl): LocationDataSource

  @Binds
  abstract fun provideRegionDataSource(regionDataSource: RegionDataSourceImpl): RegionDataSource


}