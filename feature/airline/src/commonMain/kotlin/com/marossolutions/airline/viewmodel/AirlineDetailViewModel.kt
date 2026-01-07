package com.marossolutions.airline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.domain.model.Airline
import com.marossolutions.domain.repository.AirlineRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AirlineDetailViewModel(
    private val selectedICAO: String,
    private val airlineRepository: AirlineRepository,
) : ViewModel() {

    internal val uiState = airlineRepository.airlineDetail
        .filterNotNull()
        .map {
            AirlineDetailUiState.Content(
                airline = it,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = AirlineDetailUiState.Loading
        )

    init {
        viewModelScope.launch {
            airlineRepository.fetchAirlineDetails(selectedICAO)
        }
    }

    sealed interface AirlineDetailUiState {
        data object Loading : AirlineDetailUiState

        data class Content(
            val airline: Airline,
        ) : AirlineDetailUiState
    }
}
