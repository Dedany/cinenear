package com.dedany.cinenear.data


import com.dedany.cinenear.data.datasource.RegionDataSource
import javax.inject.Inject


class RegionRepository @Inject constructor (
    private val regionDataSource: RegionDataSource
) {

    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()
}
