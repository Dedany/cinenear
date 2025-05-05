package com.dedany.cinenear.data

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock

class RegionRepositoryTest{
    @Test
    fun `findLastRegion calls RegionDataSource`(): Unit = runBlocking {
        val repository = RegionRepository(mock {
            onBlocking { findLastRegion() }.thenReturn("ES")
        })

        val region = repository.findLastRegion()

        TestCase.assertEquals("ES", region)
    }
}
