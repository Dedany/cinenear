package com.dedany.cinenear.usecases

import com.dedany.cinenear.data.MoviesRepository
import com.dedany.cinenear.domain.entities.Providers
import javax.inject.Inject

class FetchWatchProvidersUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): Providers =
        repository.fetchWatchProviders(movieId)
}
