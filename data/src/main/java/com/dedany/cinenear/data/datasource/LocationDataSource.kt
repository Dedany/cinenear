package com.dedany.cinenear.data.datasource

import com.dedany.cinenear.domain.entities.Location


interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

