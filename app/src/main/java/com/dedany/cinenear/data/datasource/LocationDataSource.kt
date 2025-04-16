package com.dedany.cinenear.data.datasource

import android.location.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

