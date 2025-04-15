package com.dedany.cinenear.data.datasource

import android.location.Location

const val DEFAULT_REGION = "US"

interface RegionDataSource {
    suspend fun findLastRegion(): String

    suspend fun Location.toRegion(): String
}

