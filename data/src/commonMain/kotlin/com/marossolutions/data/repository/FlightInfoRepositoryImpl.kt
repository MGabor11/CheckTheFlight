package com.marossolutions.data.repository

import com.marossolutions.data.datastore.FlightInfoPreferences
import com.marossolutions.data.network.FlightInfoApi
import com.marossolutions.data.network.FlightInfoResponse
import com.marossolutions.data.service.AirportRemoteDataSource
import com.marossolutions.domain.model.FlightInfo
import com.marossolutions.domain.model.FlightState
import com.marossolutions.domain.repository.FlightInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class FlightInfoRepositoryImpl(
    private val flightInfoPreferences: FlightInfoPreferences,
    private val flightInfoApi: FlightInfoApi,
    private val airportRemoteDataSource: AirportRemoteDataSource,
) : FlightInfoRepository {

    override val flightNumber: Flow<String?> = flightInfoPreferences.flightNumber

    override val flightInfo: Flow<FlightInfo?> = flightInfoPreferences.flightInfoJson.map { json ->
        json?.let { Json.decodeFromString(it) }
    }

    override suspend fun fetchFlightInfo(flightNumber: String) {
        val response = flightInfoApi.getFlightInfo(flightNumber)
        val departureAirport =
            airportRemoteDataSource.getAirportByIcao(response.flightInfo.departureAirportIcao)
        val arrivalAirport =
            response.flightInfo.arrivalAirportIcao?.let { airportRemoteDataSource.getAirportByIcao(it) }

        val flightInfo = response.toFlightInfo(departureAirport.name, arrivalAirport?.name)

        val jsonString = Json.encodeToString(flightInfo)
        flightInfoPreferences.setFlightInfoJson(jsonString)
    }

    override suspend fun setFlightNumber(flightNumber: String) {
        flightInfoPreferences.setFlightNumber(flightNumber)
    }

    override fun clearFlightNumber() {
        // TODO
    }

    private fun FlightInfoResponse.toFlightInfo(
        departureAirport: String?,
        arrivalAirport: String?,
    ): FlightInfo = with(flightInfo) {
        FlightInfo(
            flightNumber = flightIata ?: "",
            departureAirport = departureAirport ?: "",
            arrivalAirport = arrivalAirport ?: "",
            departureTime = departureTimeTs.let { (it * 1000).toLocalDateTime() },
            arrivalTime = arrivalTimeTs.let { (it * 1000).toLocalDateTime() },
            estimatedDepartureTime = estimatedDepartureTime?.let {
                (it * 1000).toLocalDateTime()
            },
            estimatedArrivalTime = estimatedArrivalTime?.let {
                (it * 1000).toLocalDateTime()
            },
            flightState = when (flightStatus) {
                "scheduled" -> FlightState.SCHEDULED
                "en-route" -> FlightState.ENROUTE
                "landed" -> FlightState.LANDED
                else -> FlightState.UNKNOWN
            },
            departureTerminal = departureTerminal,
            departureGate = departureGate,
            arrivalTerminal = arrivalTerminal,
            arrivalGate = arrivalGate,
        )
    }

    @OptIn(ExperimentalTime::class)
    private fun Long.toLocalDateTime(): LocalDateTime = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}