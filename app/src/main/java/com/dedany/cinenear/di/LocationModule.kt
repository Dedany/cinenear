package com.dedany.cinenear.di

import android.content.Context
import android.location.Geocoder
import androidx.room.PrimaryKey
import com.dedany.cinenear.App
import com.dedany.cinenear.data.datasource.LocationDataSource
import com.dedany.cinenear.data.datasource.RegionDataSource
import com.dedany.cinenear.framework.LocationDataSourceImpl
import com.dedany.cinenear.framework.RegionDataSourceImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocationAndRegionModule {

  @Binds
  abstract fun provideLocationDataSource(locationDataSource: LocationDataSourceImpl): LocationDataSource

  @Binds
  abstract fun provideRegionDataSource(regionDataSource: RegionDataSourceImpl): RegionDataSource


    @Provides
    fun provideFusedLocationProviderClient(app : App) : FusedLocationProviderClient{
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    fun provideGeocoder(app : App) : Geocoder {
        return Geocoder(app)
    }
}
