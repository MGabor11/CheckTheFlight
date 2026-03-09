package com.marossolutions.data.service

import com.marossolutions.data.network.AirlineApi
import com.marossolutions.data.network.AirlineResponse
import com.marossolutions.domain.model.Airline
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class AirlineRemoteDataSourceImpl(
    private val airlineApi: AirlineApi,
) : AirlineRemoteDataSource {

    override suspend fun getAirlineByIcao(airlineIcao: String): Airline? = fetchAirline(airlineIcao)
        ?.toAirline()

    override suspend fun getAirlinesByIcaos(airlineIcaos: List<String>): List<Airline> =
        coroutineScope {
            airlineIcaos.map {
                async { getAirlineByIcao(it) }
            }.awaitAll()
                .filterNotNull()
        }

    private suspend fun fetchAirline(airlineIcao: String) =
        airlineApi.getAirline(airlineIcao)

    private fun AirlineResponse.toAirline() = Airline(
        icao = this.icao,
        name = this.name,
        logoUrl = this.logoUrl
    )
}
