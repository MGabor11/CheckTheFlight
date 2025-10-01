package com.marossolutions.checktheflight.ui.airlines

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marossolutions.checktheflight.theme.AppTheme
import com.marossolutions.checktheflight.ui.FullScreenLoading
import com.marossolutions.domain.model.Airline
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AirlinesScreen(viewModel: AirlinesViewModel = koinViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.clearSelectedAirline()
    }

    AirlinesScreenContent(
        uiState = uiState,
        onAirlineSelected = viewModel::navigateToAirlineDetail,
    )
}

@Composable
private fun AirlinesScreenContent(
    uiState: AirlinesViewModel.AirlinesUiState,
    onAirlineSelected: (String) -> Unit,
) {
    when (uiState) {
        is AirlinesViewModel.AirlinesUiState.Content -> Content(
            airlines = uiState.airlines,
            onAirlineSelected = onAirlineSelected,
        )

        AirlinesViewModel.AirlinesUiState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun Content(
    airlines: List<Airline>,
    onAirlineSelected: (String) -> Unit,
) {
    Box {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(airlines) { index, airline ->
                Column {
                    AirlineItem(
                        airline = airline,
                        onItemClick = { onAirlineSelected(airline.icao) }
                    )
                    if (index == airlines.lastIndex) {
                        Spacer(Modifier.height(64.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AirlinesScreenContentPreview() {
    AppTheme {
        AirlinesScreenContent(
            uiState = AirlinesViewModel.AirlinesUiState.Content(
                airlines = listOf(
                    Airline(
                        icao = "DLH",
                        name = "Lufthansa",
                        logoUrl = null,
                    )
                )
            ),
            onAirlineSelected = { },
        )
    }
}