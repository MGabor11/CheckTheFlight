package com.marossolutions.airline.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import checktheflight.feature.airline.generated.resources.Res
import checktheflight.feature.airline.generated.resources.airline_detail_icao_label
import checktheflight.feature.airline.generated.resources.airline_detail_info_section
import checktheflight.feature.airline.generated.resources.airline_detail_name_label
import checktheflight.feature.airline.generated.resources.airline_detail_title
import checktheflight.feature.airline.generated.resources.error_icon
import com.marossolutions.airline.viewmodel.AirlineDetailViewModel
import com.marossolutions.component.FullScreenLoading
import com.marossolutions.domain.model.Airline
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AirlineDetailScreen(viewModel: AirlineDetailViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AirlineDetailScreenContent(uiState = uiState)
}

@Composable
private fun AirlineDetailScreenContent(
    uiState: AirlineDetailViewModel.AirlineDetailUiState,
) {
    when (uiState) {
        is AirlineDetailViewModel.AirlineDetailUiState.Content -> Content(airline = uiState.airline)
        AirlineDetailViewModel.AirlineDetailUiState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun Content(airline: Airline) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Header card: logo + name
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Logo
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    airline.logoUrl?.let { url ->
                        when (val resource = asyncPainterResource(url)) {
                            is Resource.Loading -> CircularProgressIndicator(
                                modifier = Modifier.size(28.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            is Resource.Success -> Image(
                                modifier = Modifier.fillMaxSize().padding(8.dp),
                                contentScale = ContentScale.Fit,
                                painter = resource.value,
                                contentDescription = airline.name,
                            )
                            is Resource.Failure -> Image(
                                modifier = Modifier.fillMaxSize().padding(12.dp),
                                painter = painterResource(Res.drawable.error_icon),
                                contentDescription = null,
                            )
                        }
                    } ?: Icon(
                        imageVector = Icons.Filled.Flight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(40.dp),
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column {
                    Text(
                        text = stringResource(Res.string.airline_detail_title),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = airline.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }

        // Details card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = stringResource(Res.string.airline_detail_info_section),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                DetailRow(
                    label = stringResource(Res.string.airline_detail_name_label),
                    value = airline.name,
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                DetailRow(
                    label = stringResource(Res.string.airline_detail_icao_label),
                    value = airline.icao,
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
    }
}
