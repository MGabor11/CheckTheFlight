package com.marossolutions.checktheflight.ui.airports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.checktheflight.navigation.AppScreen
import com.marossolutions.checktheflight.navigation.SimpleNavigator
import com.marossolutions.domain.repository.AirportRepository
import com.marossolutions.domain.model.Airport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class AirportsViewModel(
    private val airportRepository: AirportRepository,
    private val navigator: SimpleNavigator,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)

    val uiState = combine(
        airportRepository.airports,
        _isLoading,


        ) { airports, isLoading ->
        when {
            airports.isEmpty() || isLoading -> AirportsUiState.Loading
            airports.isNotEmpty() -> AirportsUiState.Content(airports)
            else -> error("Unsupported UI state scenario")
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = AirportsUiState.Loading
    )

    init {
        viewModelScope.launch {
            _isLoading.value = true
            airportRepository.fetchAirports()
            _isLoading.value = false
        }
    }

    fun clearSelectedAirport() {
        airportRepository.clearSelectedAirport()
    }

    fun navigateToAirportDetail(icao: String) {
        navigator.navigateTo(AppScreen.ScreenAirportDetail(icao))
    }

    sealed interface AirportsUiState {
        data object Loading : AirportsUiState

        data class Content(
            val airports: List<Airport>,
        ) : AirportsUiState
    }
}
