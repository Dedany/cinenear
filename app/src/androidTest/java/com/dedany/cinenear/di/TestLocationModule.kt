package com.dedany.cinenear.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.Locale

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocationAndRegionProvideModule::class]
)
object TestLocationModule {

    @Provides
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    // Ya no necesitamos un método para Geocoder aquí porque lo tenemos en TestFrameworkModule
} 