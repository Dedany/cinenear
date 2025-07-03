package com.dedany.cinenear.usecases

import com.dedany.cinenear.data.MoviesRepository
import com.dedany.cinenear.domain.entities.Providers
import javax.inject.Inject

class FetchProvidersUseCase @Inject constructor(
private val repository: MoviesRepository
){
    suspend operator fun invoke(movieId: Int) : Providers {
        return repository.fetchWatchProviders(movieId)
    }
}