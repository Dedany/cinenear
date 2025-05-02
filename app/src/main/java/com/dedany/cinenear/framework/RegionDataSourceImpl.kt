package com.dedany.cinenear.framework

import android.location.Geocoder
import com.dedany.cinenear.ui.common.getFromLocationCompat
import com.dedany.cinenear.data.datasource.DEFAULT_REGION
import com.dedany.cinenear.data.datasource.LocationDataSource
import com.dedany.cinenear.data.datasource.RegionDataSource
import com.dedany.cinenear.domain.entities.Location
import javax.inject.Inject


class RegionDataSourceImpl @Inject constructor(
    private val geocoder: Geocoder,
    private val locationDataSource: LocationDataSource
) : RegionDataSource {

    override suspend fun findLastRegion(): String =
        locationDataSource.findLastLocation()?.toRegion() ?: DEFAULT_REGION

    private suspend fun Location.toRegion(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val region = addresses.firstOrNull()?.countryCode
        return region ?: DEFAULT_REGION
    }
}