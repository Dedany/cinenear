package com.dedany.cinenear.data


import com.dedany.cinenear.data.datasource.RegionDataSource


class RegionRepository(private val regionDataSource: RegionDataSource) {

    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()
}
