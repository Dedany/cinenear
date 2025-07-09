package com.dedany.cinenear.data

import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.data.datasource.MoviesLocalDataSource
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.domain.model.MovieWithProviders
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) {

    private val _providersMap = MutableStateFlow<Map<Int, Providers>>(emptyMap())
    val providersMap: StateFlow<Map<Int, Providers>> = _providersMap

  val movies: Flow<List<Movie>> = localDataSource.movies.onEach { localMovies ->
        if (localMovies.isEmpty()) {
            val remoteMovies = remoteDataSource.fetchPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(remoteMovies)
        }
    }

    val moviesWithProviders: Flow<List<MovieWithProviders>> = combine(
        movies,
        providersMap
    ) { movieList, providerMap ->
        movieList.map { movie ->
            MovieWithProviders(
                movie = movie,
                providers = providerMap[movie.id]
            )
        }
    }

    fun findMovieById(id: Int): Flow<Movie> = localDataSource.findMovieById(id)
        .onEach { movie ->
            if (movie == null) {
                val remoteMovie = remoteDataSource.findMovieById(id)
                localDataSource.save(listOf(remoteMovie))
            }
        }
        .filterNotNull()

    suspend fun toggleFavorite(movie: Movie) {
        localDataSource.save(listOf(movie.copy(isFavorite = !movie.isFavorite)))
    }

    suspend fun fetchWatchProviders(movieId: Int): Providers {
        return remoteDataSource.fetchWatchProviders(movieId)
    }
}
