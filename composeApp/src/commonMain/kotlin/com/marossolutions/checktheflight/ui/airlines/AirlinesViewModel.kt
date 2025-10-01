package com.marossolutions.checktheflight.ui.airlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.checktheflight.navigation.AppScreen
import com.marossolutions.checktheflight.navigation.SimpleNavigator
import com.marossolutions.domain.repository.AirlineRepository
import com.marossolutions.domain.model.Airline
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class AirlinesViewModel(
    private val airlineRepository: AirlineRepository,
    private val navigator: SimpleNavigator,
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
        navigator.navigateTo(AppScreen.ScreenAirlineDetail(icao))
    }

    sealed interface AirlinesUiState {
        data object Loading : AirlinesUiState

        data class Content(
            val airlines: List<Airline>,
        ) : AirlinesUiState
    }
}