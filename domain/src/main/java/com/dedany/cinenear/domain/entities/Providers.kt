package com.dedany.cinenear.domain.entities


data class Providers(
    val link: String,
    val buy: List<Provider>,
    val rent: List<Provider>,
    val flatrate: List<Provider>
)

data class Provider(
    val id: Int,
    val name: String,
    val logoPath: String?
)
