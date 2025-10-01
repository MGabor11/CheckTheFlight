package com.marossolutions.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AirportResponse(
    @SerialName("icao") val icao: String,
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("city") val city: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("timezone") val timezone: String,
)
