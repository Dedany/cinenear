package com.dedany.cinenear.domain.model

import com.dedany.cinenear.domain.entities.Movie
import com.dedany.cinenear.domain.entities.Providers

data class MovieWithProviders(
    val movie: Movie,
    val providers: Providers?
)
