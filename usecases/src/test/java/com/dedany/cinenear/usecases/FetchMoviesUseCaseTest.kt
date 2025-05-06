package com.dedany.cinenear.usecases

import com.dedany.cinenear.sampleMovies
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FetchMoviesUseCaseTest {

    @Test
    fun `Invoke calls repository`() {
        val movieFlow = flowOf(sampleMovies(1,3,4))
            val useCase = FetchMoviesUseCase (mock {
            on { movies } doReturn movieFlow
        })
        val result = useCase()

        assertEquals(movieFlow, result)
    }
}