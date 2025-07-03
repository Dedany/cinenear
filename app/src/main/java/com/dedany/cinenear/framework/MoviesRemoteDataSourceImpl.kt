package com.dedany.cinenear.framework

import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers
import com.dedany.cinenear.domain.entities.Provider
import com.dedany.cinenear.framework.remote.MoviesService
import com.dedany.cinenear.framework.remote.ProviderCountry
import com.dedany.cinenear.framework.remote.RemoteMovie
import com.dedany.cinenear.framework.remote.Provider as RemoteProvider
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val moviesService: MoviesService
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(region: String): List<Movie> =
        moviesService.fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    override suspend fun findMovieById(id: Int): Movie =
        moviesService.fetchMovieById(id)
            .toDomainModel()

    override suspend fun fetchWatchProviders(id: Int): Providers {
        val response = moviesService.fetchWatchProviders(id)

        val countryProviders = response.results["ES"] ?: ProviderCountry(
            link = "",
            buy = emptyList(),
            rent = emptyList(),
            flatrate = emptyList()
        )

        return countryProviders.toProvidersDomain()


    }

    private fun RemoteMovie.toDomainModel(): Movie =
        Movie(
            id = id,
            title = title,
            overview = overview,
            releaseDate = releaseDate,
            poster = "https://image.tmdb.org/t/p/w185/$posterPath",
            backdrop = if (backdropPath != null) "https://image.tmdb.org/t/p/w780/$backdropPath" else null,
            originalTitle = originalTitle,
            originalLanguage = originalLanguage,
            popularity = String.format("%.1f", popularity.toFloat()),
            voteAverage = String.format("%.1f", voteAverage.toFloat()),
            isFavorite = false
        )

    private fun ProviderCountry.toProvidersDomain(): Providers =
        Providers(
            link = this.link,
            buy = this.buy?.map { it.toProviderDomain() } ?: emptyList(),
            rent = this.rent?.map { it.toProviderDomain() } ?: emptyList(),
            flatrate = this.flatrate?.map { it.toProviderDomain() } ?: emptyList()
        )

    private fun RemoteProvider.toProviderDomain(): Provider =
        Provider(
            id = provider_id,
            name = provider_name,
            logoPath = logo_path
        )
}
