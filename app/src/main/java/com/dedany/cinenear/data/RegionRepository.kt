package com.dedany.cinenear.data

import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.dedany.cinenear.ui.common.getFromLocationCompat
import com.dedany.cinenear.ui.common.lastLocation
import com.google.android.gms.location.LocationServices


const val DEFAULT_REGION = "US"

class RegionRepository (app: Application) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(app)
    private val geocoder = Geocoder(app)

    suspend fun findLastRegion(): String = fusedLocationClient.lastLocation()?.toRegion() ?: DEFAULT_REGION

    private suspend fun Location.toRegion(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val region = addresses.firstOrNull()?.countryCode
        return region ?: DEFAULT_REGION
    }
}
