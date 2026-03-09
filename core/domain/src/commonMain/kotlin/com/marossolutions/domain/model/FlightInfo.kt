package com.marossolutions.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class FlightInfo(
    val flightNumber: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureTime: LocalDateTime,
    val arrivalTime: LocalDateTime,
    val estimatedDepartureTime: LocalDateTime?,
    val estimatedArrivalTime: LocalDateTime?,
    val flightState: FlightState,
    val departureTerminal: String?,
    val departureGate: String?,
    val arrivalTerminal: String?,
    val arrivalGate: String?,
)

enum class FlightState {
    SCHEDULED,
    ENROUTE,
    LANDED,
    UNKNOWN
}
