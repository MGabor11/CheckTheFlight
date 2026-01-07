package com.marossolutions.airport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.domain.model.Airport
import com.marossolutions.domain.usecase.ObserveAirportsUseCase
import com.marossolutions.domain.usecase.RefreshAirportsUseCase
import com.marossolutions.navigation.Navigator
import com.marossolutions.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class AirportsViewModel(
    observeAirportsUseCase: ObserveAirportsUseCase,
    private val refreshAirportsUseCase: RefreshAirportsUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)

    val uiState = combine(
        observeAirportsUseCase(),
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
            refreshAirportsUseCase()
            _isLoading.value = false
        }
    }

    fun clearSelectedAirport() {
        // Note: clearSelectedAirport is still on repository, consider adding to use case if needed
    }

    fun navigateToAirportDetail(icao: String) {
        navigator.navigateTo(Route.ScreenAirportDetail(icao))
    }

    sealed interface AirportsUiState {
        data object Loading : AirportsUiState

        data class Content(
            val airports: List<Airport>,
        ) : AirportsUiState
    }
}
