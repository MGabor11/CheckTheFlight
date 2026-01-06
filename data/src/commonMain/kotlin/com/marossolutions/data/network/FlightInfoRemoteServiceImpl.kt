package com.marossolutions.data.network

import com.marossolutions.common.dispatcher.DispatcherProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.withContext

internal class FlightInfoApiImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val airlabsBaseUrl: String,
) : FlightInfoApi {

    override suspend fun getFlightInfo(flightIata: String): FlightInfoResponse =
        withContext(dispatcherProvider.io) {
            return@withContext httpClient.get(airlabsBaseUrl + "flight?&flight_iata=$flightIata")
                .body<FlightInfoResponse>()
        }
}