package com.marossolutions.airport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import checktheflight.feature.airport.generated.resources.Res
import checktheflight.feature.airport.generated.resources.airports_title
import com.marossolutions.airport.viewmodel.AirportsViewModel
import com.marossolutions.component.FullScreenLoading
import com.marossolutions.domain.model.Airport
import org.jetbrains.compose.resources.stringResource
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
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Text(
                text = stringResource(Res.string.airports_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
        }
        itemsIndexed(airports) { _, airport ->
            AirportItem(
                airport = airport,
                onAirportClick = { onAirportSelected(airport.icao) },
            )
        }
    }
}
