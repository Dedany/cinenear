package com.dedany.cinenear.data.datasource

import com.dedany.cinenear.domain.Location


interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

