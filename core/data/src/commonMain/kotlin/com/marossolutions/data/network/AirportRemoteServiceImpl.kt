package com.marossolutions.data.network

import com.marossolutions.common.dispatcher.DispatcherProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.withContext

internal class AirportApiImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val apiNinjaBaseUrl: String,
) : AirportApi {

    override suspend fun getAirports(countryCode: String): List<AirportResponse> = withContext(dispatcherProvider.io) {
        return@withContext httpClient.get(apiNinjaBaseUrl + "airports?country=" + countryCode)
            .body<List<AirportResponse>>()
    }

    override suspend fun getAirportByICAO(icao: String): AirportResponse = withContext(dispatcherProvider.io) {
        val response = httpClient.get(apiNinjaBaseUrl + "airports?icao=" + icao).body<List<AirportResponse>>()
        return@withContext response.first()
    }
}