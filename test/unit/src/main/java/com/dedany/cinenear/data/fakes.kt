package com.dedany.cinenear.data

import com.dedany.cinenear.data.datasource.DEFAULT_REGION
import com.dedany.cinenear.data.datasource.MoviesLocalDataSource
import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.data.datasource.RegionDataSource
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Provider
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.sampleMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map


fun buildMoviesRepositoryWith(
    localData: List<Movie> = emptyList(),
    remoteData: List<Movie> = emptyList()
): MoviesRepository {
    val regionDataSource = FakeRegionDataSource()
    val regionRepository = RegionRepository(regionDataSource)
    val localDataSource = FakeLocalDataSource().apply { inMemoryMovies.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteData }
    return MoviesRepository(regionRepository, localDataSource, remoteDataSource)
}

class FakeLocalDataSource : MoviesLocalDataSource {

    val inMemoryMovies = MutableStateFlow<List<Movie>>(emptyList())

    override val movies = inMemoryMovies

    override fun findMovieById(id: Int): Flow<Movie?> =
        inMemoryMovies.map { it.firstOrNull { movie -> movie.id == id } }

    override suspend fun save(movies: List<Movie>) {
        inMemoryMovies.value = movies
    }
}


class FakeRemoteDataSource : MoviesRemoteDataSource {

    var movies = sampleMovies(1, 2, 3, 4)

    override suspend fun fetchPopularMovies(region: String) = movies

    override suspend fun findMovieById(id: Int): Movie = movies.first { it.id == id }

    override suspend fun fetchWatchProviders(id: Int): Providers {
        return Providers(
            link = "https://fake.link/movie/$id",
            buy = listOf(Provider(id = 1, name = "FakeBuy", logoPath = null)),
            rent = listOf(Provider(id = 2, name = "FakeRent", logoPath = null)),
            flatrate = listOf(Provider(id = 3, name = "FakeFlatrate", logoPath = null))
        )
    }
}

class FakeRegionDataSource : RegionDataSource {

    var region = DEFAULT_REGION

    override suspend fun findLastRegion(): String = region

}