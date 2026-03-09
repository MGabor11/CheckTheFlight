package com.marossolutions.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marossolutions.component.FlightNumberTextField
import com.marossolutions.component.FullScreenLoading
import com.marossolutions.home.viewmodel.HomeViewModel
import com.marossolutions.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is HomeViewModel.HomeUiState.Content -> HomeScreenContent(
            contentState = state,
            onSetFlightNumber = viewModel::setFlightNumber,
            onStartBackgroundFetch = viewModel::startBackgroundFetching,
            onStopBackgroundFetch = viewModel::stopBackgroundFetching,
            onRefreshFlightInfo = viewModel::refreshFlightInfo
        )

        HomeViewModel.HomeUiState.Loading -> FullScreenLoading()
        HomeViewModel.HomeUiState.Error -> {}//TODO
    }
}

@Composable
private fun HomeScreenContent(
    contentState: HomeViewModel.HomeUiState.Content,
    onSetFlightNumber: (String) -> Unit,
    onStartBackgroundFetch: () -> Unit,
    onStopBackgroundFetch: () -> Unit,
    onRefreshFlightInfo: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Home:\n$contentState",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your flight number:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            var flightNumber by remember { mutableStateOf(contentState.flightNumber ?: "") }

            FlightNumberTextField(
                value = flightNumber,
                onValueChange = { flightNumber = it },
                onFocusChange = { isFocused ->
                    if (!isFocused && flightNumber.isNotEmpty()) {
                        onSetFlightNumber(flightNumber)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onStartBackgroundFetch) {
                Text("Start background fetching")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onStopBackgroundFetch) {
                Text("Stop background fetching")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onRefreshFlightInfo) {
                Text("Refresh flight info")
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun HomeScreenContentPreview() {
    AppTheme {
        HomeScreenContent(
            contentState = HomeViewModel.HomeUiState.Content(
                flightNumber = "LH1234",
                isBackgroundFetchingInProgress = true,
                lastBackgroundFetchTime = Clock.System.now(),
                lastSuccessfulBackgroundFetchTime = Clock.System.now(),
            ),
            onSetFlightNumber = {},
            onStartBackgroundFetch = {},
            onStopBackgroundFetch = {},
            onRefreshFlightInfo = {},
        )
    }
}
