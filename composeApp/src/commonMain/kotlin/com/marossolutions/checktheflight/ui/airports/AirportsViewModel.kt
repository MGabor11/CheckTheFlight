package com.marossolutions.checktheflight.ui.airports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.checktheflight.navigation.AppScreen
import com.marossolutions.checktheflight.navigation.SimpleNavigator
import com.marossolutions.domain.model.Airport
import com.marossolutions.domain.usecase.ObserveAirportsUseCase
import com.marossolutions.domain.usecase.RefreshAirportsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class AirportsViewModel(
    observeAirportsUseCase: ObserveAirportsUseCase,
    private val refreshAirportsUseCase: RefreshAirportsUseCase,
    private val navigator: SimpleNavigator,
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
        navigator.navigateTo(AppScreen.ScreenAirportDetail(icao))
    }

    sealed interface AirportsUiState {
        data object Loading : AirportsUiState

        data class Content(
            val airports: List<Airport>,
        ) : AirportsUiState
    }
}
