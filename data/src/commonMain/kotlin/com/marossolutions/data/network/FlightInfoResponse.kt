package com.marossolutions.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlightInfoResponse(
    @SerialName("response") val flightInfo: FlightInfo
)

@Serializable
 data class FlightInfo(
    @SerialName("airline_iata") val airlineIata: String?,
    @SerialName("airline_icao") val airlineIcao: String,
    @SerialName("flight_iata") val flightIata: String?,
    @SerialName("flight_icao") val flightIcao: String,
    @SerialName("dep_icao") val departureAirportIcao: String,
    @SerialName("dep_terminal") val departureTerminal: String?,
    @SerialName("dep_gate") val departureGate: String?,
    @SerialName("arr_icao") val arrivalAirportIcao: String?,
    @SerialName("arr_terminal") val arrivalTerminal: String?,
    @SerialName("arr_gate") val arrivalGate: String?,
    @SerialName("status") val flightStatus: String,
    @SerialName("dep_time_ts") val departureTimeTs: Long,
    @SerialName("arr_time_ts") val arrivalTimeTs: Long,
    @SerialName("arr_actual_ts") val actualArrivalTimeTs: Long?,
    @SerialName("dep_actual_ts") val actualDepartureTimeTs: Long?,
    @SerialName("arr_estimated_ts") val estimatedArrivalTime: Long?,
    @SerialName("dep_estimated_ts") val estimatedDepartureTime: Long?,
)