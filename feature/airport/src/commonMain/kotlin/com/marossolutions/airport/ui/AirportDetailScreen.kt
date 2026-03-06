package com.marossolutions.airport.ui

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
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import checktheflight.feature.airport.generated.resources.Res
import checktheflight.feature.airport.generated.resources.airport_detail_city_label
import checktheflight.feature.airport.generated.resources.airport_detail_coordinates_label
import checktheflight.feature.airport.generated.resources.airport_detail_icao_label
import checktheflight.feature.airport.generated.resources.airport_detail_info_section
import checktheflight.feature.airport.generated.resources.airport_detail_location_section
import checktheflight.feature.airport.generated.resources.airport_detail_name_label
import checktheflight.feature.airport.generated.resources.airport_detail_timezone_label
import checktheflight.feature.airport.generated.resources.airport_detail_title
import com.marossolutions.airport.viewmodel.AirportDetailViewModel
import com.marossolutions.component.FullScreenLoading
import com.marossolutions.domain.model.Airport
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AirportDetailScreen(viewModel: AirportDetailViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AirportDetailScreenContent(uiState = uiState)
}

@Composable
private fun AirportDetailScreenContent(
    uiState: AirportDetailViewModel.AirportDetailUiState,
) {
    when (uiState) {
        is AirportDetailViewModel.AirportDetailUiState.Content -> Content(airport = uiState.airport)
        AirportDetailViewModel.AirportDetailUiState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun Content(airport: Airport) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Header card: icon + name + city
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
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp),
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column {
                    Text(
                        text = stringResource(Res.string.airport_detail_title),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = airport.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = airport.city,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                    )
                }
            }
        }

        // Information card
        DetailCard(title = stringResource(Res.string.airport_detail_info_section)) {
            DetailRow(
                icon = Icons.Filled.LocationOn,
                label = stringResource(Res.string.airport_detail_name_label),
                value = airport.name,
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            DetailRow(
                icon = Icons.Filled.MyLocation,
                label = stringResource(Res.string.airport_detail_icao_label),
                value = airport.icao,
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            DetailRow(
                icon = Icons.Filled.LocationOn,
                label = stringResource(Res.string.airport_detail_city_label),
                value = airport.city,
            )
        }

        // Location card
        DetailCard(title = stringResource(Res.string.airport_detail_location_section)) {
            DetailRow(
                icon = Icons.Filled.MyLocation,
                label = stringResource(Res.string.airport_detail_coordinates_label),
                value = "${airport.latitude}, ${airport.longitude}",
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            DetailRow(
                icon = Icons.Filled.Language,
                label = stringResource(Res.string.airport_detail_timezone_label),
                value = airport.timeZone.toString(),
            )
        }
    }
}

@Composable
private fun DetailCard(
    title: String,
    content: @Composable () -> Unit,
) {
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
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            content()
        }
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
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
