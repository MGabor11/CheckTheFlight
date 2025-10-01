package com.marossolutions.checktheflight.ui.airportdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marossolutions.checktheflight.ui.FullScreenLoading
import com.marossolutions.domain.model.Airport

@Composable
internal fun AirportDetailScreen(viewModel: AirportDetailViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
    )
}

@Composable
private fun HomeScreenContent(
    uiState: AirportDetailViewModel.AirportDetailUiState,
) {
    when (uiState) {
        is AirportDetailViewModel.AirportDetailUiState.Content -> Content(
            airport = uiState.airport,
        )

        AirportDetailViewModel.AirportDetailUiState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun Content(
    airport: Airport
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = airport.name,
            fontSize = 18.sp,
        )

        Text(airport.city, fontSize = 16.sp)
    }
}
