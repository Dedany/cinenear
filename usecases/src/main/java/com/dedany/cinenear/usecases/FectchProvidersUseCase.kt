package com.dedany.cinenear.usecases

import com.dedany.cinenear.data.MoviesRepository
import com.dedany.cinenear.domain.entities.Providers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchProvidersUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Providers> = flow {
        emit(repository.fetchWatchProviders(id))
    }
}
