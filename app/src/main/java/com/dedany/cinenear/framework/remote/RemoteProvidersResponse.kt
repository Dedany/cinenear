package com.dedany.cinenear.framework.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteProvidersResponse(
    val id: Int,
    val results:Map<String, ProviderCountry>
)

@Serializable
data class ProviderCountry(
    val link: String,
    val buy: List<Provider>? =null,
    val rent: List<Provider>? =null,
    val flatrate: List<Provider>? =null,
)

@Serializable
data class Provider(
    @SerialName("provider_id")
    val providerId: Int,
    @SerialName("provider_name")
    val providerName: String,
    @SerialName("logo_path")
    val logoPath: String? = null
)
