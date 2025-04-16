package com.dedany.cinenear.framework

import android.annotation.SuppressLint
import android.location.Location
import com.dedany.cinenear.data.datasource.LocationDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
private suspend fun FusedLocationProviderClient.lastLocation(): Location? {
    return suspendCancellableCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resume(location)
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}
class LocationDataSourceimpl(
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationDataSource {

        override suspend fun findLastLocation(): Location? = fusedLocationClient.lastLocation()
}