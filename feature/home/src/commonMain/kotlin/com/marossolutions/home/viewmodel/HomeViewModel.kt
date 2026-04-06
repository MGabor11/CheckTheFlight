package com.marossolutions.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.domain.repository.FlightInfoFetchingSettingsRepository
import com.marossolutions.domain.repository.FlightInfoRepository
import com.marossolutions.flightsync.FlightInfoFetchService
import com.marossolutions.flightsync.FlightInfoSyncManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
internal class HomeViewModel(
    private val flightSyncManager: FlightInfoSyncManager,
    private val flightInfoRepository: FlightInfoRepository,
    private val flightInfoFetchingSettingsRepository: FlightInfoFetchingSettingsRepository,
    private val flightInfoFetchService: FlightInfoFetchService,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    val uiState = combine(
        _isLoading,
        flightInfoRepository.flightNumber,
        flightInfoFetchingSettingsRepository.isPeriodicFlightInfoFetchingEnabled,
        flightInfoFetchingSettingsRepository.lastBackgroundFetchTime,
        flightInfoFetchingSettingsRepository.lastSuccessfulBackgroundFetchTime
    ) { isLoading,
        flightNumber,
        isPeriodicFlightInfoFetchingEnabled,
        lastBackgroundFetchTime,
        lastSuccessfulBackgroundFetchTime ->
        when {
            isLoading -> HomeUiState.Loading
            else -> HomeUiState.Content(
                flightNumber = flightNumber,
                isBackgroundFetchingInProgress = isPeriodicFlightInfoFetchingEnabled,
                lastBackgroundFetchTime = lastBackgroundFetchTime,
                lastSuccessfulBackgroundFetchTime = lastSuccessfulBackgroundFetchTime
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = HomeUiState.Loading
    )

    fun startBackgroundFetching() {
        viewModelScope.launch {
            flightSyncManager.startFlightBackgroundSync()
        }
    }

    fun stopBackgroundFetching() {
        viewModelScope.launch {
            flightSyncManager.stopFlightBackgroundSync()
        }
    }

    fun setFlightNumber(flightNumber: String) {
        viewModelScope.launch {
            flightInfoRepository.setFlightNumber(flightNumber)
        }
    }

    fun refreshFlightInfo() {
        viewModelScope.launch {
            flightInfoFetchService.fetchFlightInfo(true)
        }
    }

    sealed interface HomeUiState {
        data object Loading : HomeUiState

        data class Content(
            val flightNumber: String?,
            val isBackgroundFetchingInProgress: Boolean,
            val lastBackgroundFetchTime: Instant?,
            val lastSuccessfulBackgroundFetchTime: Instant?,
        ) : HomeUiState

        data object Error : HomeUiState
    }
}