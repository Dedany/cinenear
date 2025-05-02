package com.dedany.cinenear.usecases

import com.dedany.cinenear.domain.Movie
import com.dedany.cinenear.data.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMovieByIdUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Movie> = repository.findMovieById(id)
}