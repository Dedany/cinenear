package com.dedany.cinenear.di

import android.location.Geocoder
import com.dedany.cinenear.App
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LocationAndRegionProvideModule {


    @Provides
    fun provideFusedLocationProviderClient(app: App): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    fun provideGeocoder(app: App): Geocoder {
        return Geocoder(app)
    }
}