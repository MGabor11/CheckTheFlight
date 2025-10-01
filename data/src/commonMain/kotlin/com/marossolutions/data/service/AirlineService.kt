package com.marossolutions.data.service

import com.marossolutions.domain.model.Airline

interface AirlineService {

    suspend fun getAirlineByIcao(airlineIcao: String) : Airline?

    suspend fun getAirlinesByIcaos(airlineIcaos: List<String>) : List<Airline>
}