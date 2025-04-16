package com.dedany.cinenear.framework

import android.annotation.SuppressLint
import com.dedany.cinenear.data.datasource.LocationDataSource
import com.dedany.cinenear.domain.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import android.location.Location as AndroidLocation


class LocationDataSourceimpl(
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationDataSource {

    override suspend fun findLastLocation(): Location? = fusedLocationClient.lastLocation()
}

@SuppressLint("MissingPermission")
private suspend fun FusedLocationProviderClient.lastLocation(): Location? {
    return suspendCancellableCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resume(location.toDomainLocation())
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}

private fun AndroidLocation.toDomainLocation(): Location = Location(latitude, longitude)