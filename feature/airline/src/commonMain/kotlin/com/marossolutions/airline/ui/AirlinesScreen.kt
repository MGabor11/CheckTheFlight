package com.marossolutions.airline.ui

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
import checktheflight.feature.airline.generated.resources.Res
import checktheflight.feature.airline.generated.resources.airlines_title
import com.marossolutions.airline.viewmodel.AirlinesViewModel
import com.marossolutions.domain.model.Airline
import com.marossolutions.component.FullScreenLoading
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AirlinesScreen(viewModel: AirlinesViewModel = koinViewModel()) {

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
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Text(
                text = stringResource(Res.string.airlines_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            )
        }
        itemsIndexed(airlines) { _, airline ->
            AirlineItem(
                airline = airline,
                onItemClick = { onAirlineSelected(airline.icao) },
            )
        }
    }
}
