package com.marossolutions.data.service

import com.marossolutions.data.network.AirportApi
import com.marossolutions.data.network.AirportResponse
import com.marossolutions.domain.model.Airport
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.TimeZone

internal class AirportRemoteDataSourceImpl(
    private val airportApi: AirportApi,
) : AirportRemoteDataSource {

    override suspend fun getAirportByIcao(icao: String): Airport = fetchAirport(icao).toAirport()

    override suspend fun getAirportsByIcaos(icaos: List<String>): List<Airport> = coroutineScope {
        icaos.map {
            async { getAirportByIcao(it) }
        }.awaitAll()
    }

    private fun AirportResponse.toAirport() = Airport(
        icao = this.icao,
        name = this.name,
        city = this.city,
        latitude = this.latitude.toString(),
        longitude = this.longitude.toString(),
        timeZone = TimeZone.of(this.timezone)
    )

    private suspend fun fetchAirport(icao: String): AirportResponse =
        airportApi.getAirportByICAO(icao)
}
