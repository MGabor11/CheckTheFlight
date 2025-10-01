package com.marossolutions.checktheflight.ui.airportdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.domain.repository.AirportRepository
import com.marossolutions.domain.model.Airport
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AirportDetailViewModel(
    private val selectedICAO: String,
    private val airportRepository: AirportRepository,
) : ViewModel() {

    internal val uiState = airportRepository.airportDetail
        .filterNotNull()
        .map {
            AirportDetailUiState.Content(
                airport = it,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = AirportDetailUiState.Loading
        )

    init {
        viewModelScope.launch {
            airportRepository.fetchAirportDetails(selectedICAO)
        }
    }

    internal sealed interface AirportDetailUiState {
        data object Loading : AirportDetailUiState

        data class Content(
            val airport: Airport,
        ) : AirportDetailUiState
    }
}
