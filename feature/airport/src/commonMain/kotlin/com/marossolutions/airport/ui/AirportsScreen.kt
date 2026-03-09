package com.marossolutions.airport.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marossolutions.airport.viewmodel.AirportsViewModel
import com.marossolutions.component.FullScreenLoading
import com.marossolutions.domain.model.Airport
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AirportsScreen(viewModel: AirportsViewModel = koinViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.clearSelectedAirport()
    }

    AirportsScreenContent(
        uiState = uiState,
        onAirportSelected = viewModel::navigateToAirportDetail,
    )
}

@Composable
private fun AirportsScreenContent(
    uiState: AirportsViewModel.AirportsUiState,
    onAirportSelected: (String) -> Unit,
) {
    when (uiState) {
        is AirportsViewModel.AirportsUiState.Content -> Content(
            airports = uiState.airports,
            onAirportSelected = onAirportSelected,
        )

        AirportsViewModel.AirportsUiState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun Content(
    airports: List<Airport>,
    onAirportSelected: (String) -> Unit,
) {
    Box {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(airports) { index, airport ->
                Column {
                    AirportItem(
                        airport = airport,
                        onAirportClick = { onAirportSelected(airport.icao) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (index == airports.lastIndex) {
                        Spacer(Modifier.height(64.dp))
                    }
                }
            }
        }
    }
}
