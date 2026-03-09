package com.marossolutions.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AirlineResponse(
    @SerialName("icao") val icao: String,
    @SerialName("name") val name: String,
    @SerialName("logo_url") val logoUrl: String?,
)
