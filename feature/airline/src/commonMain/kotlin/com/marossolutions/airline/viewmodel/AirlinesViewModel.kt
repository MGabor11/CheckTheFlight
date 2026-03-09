package com.marossolutions.airline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.domain.model.Airline
import com.marossolutions.domain.repository.AirlineRepository
import com.marossolutions.navigation.Navigator
import com.marossolutions.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AirlinesViewModel(
    private val airlineRepository: AirlineRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)

    val uiState = combine(
        airlineRepository.airlines,
        _isLoading,
    ) { airlines, isLoading ->
        when {
            airlines.isEmpty() || isLoading -> AirlinesUiState.Loading
            airlines.isNotEmpty() -> AirlinesUiState.Content(airlines)
            else -> error("Unsupported UI state scenario")
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = AirlinesUiState.Loading
    )

    init {
        viewModelScope.launch {
            _isLoading.value = true
            airlineRepository.fetchAirlines()
            _isLoading.value = false
        }
    }

    fun clearSelectedAirline() {
        airlineRepository.clearSelectedAirline()
    }

    fun navigateToAirlineDetail(icao: String) {
        navigator.navigateTo(Route.ScreenAirlineDetail(icao))
    }

    sealed interface AirlinesUiState {
        data object Loading : AirlinesUiState

        data class Content(
            val airlines: List<Airline>,
        ) : AirlinesUiState
    }
}

