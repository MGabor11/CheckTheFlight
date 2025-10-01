package com.marossolutions.data.service

import com.marossolutions.data.network.AirlineRemoteService
import com.marossolutions.data.network.AirlineResponse
import com.marossolutions.domain.model.Airline
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class AirlineServiceImpl(
    private val airlineRemoteService: AirlineRemoteService,
) : AirlineService {

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
        airlineRemoteService.getAirline(airlineIcao)

    private fun AirlineResponse.toAirline() = Airline(
        icao = this.icao,
        name = this.name,
        logoUrl = this.logoUrl
    )
}
