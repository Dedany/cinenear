package com.dedany.cinenear.framework

import com.dedany.cinenear.data.datasource.MoviesRemoteDataSource
import com.dedany.cinenear.domain.Movie
import com.dedany.cinenear.framework.remote.MoviesService
import com.dedany.cinenear.framework.remote.RemoteMovie

class MoviesRemoteDataSourceImpl(
    private val moviesService: MoviesService
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(region: String): List<Movie> =
        moviesService.fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    override suspend fun findMovieById(id: Int): Movie =
        moviesService.fetchMovieById(id)
            .toDomainModel()
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
        favorite = false
    )