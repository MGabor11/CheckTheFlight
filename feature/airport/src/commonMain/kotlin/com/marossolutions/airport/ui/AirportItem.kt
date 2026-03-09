package com.marossolutions.airport.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import checktheflight.feature.airport.generated.resources.Res
import checktheflight.feature.airport.generated.resources.airport_building_icon
import com.marossolutions.domain.model.Airport
import org.jetbrains.compose.resources.painterResource

@Composable
fun AirportItem(airport: Airport, onAirportClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clickable {
                onAirportClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(8.dp),
            painter = painterResource(Res.drawable.airport_building_icon),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = airport.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = airport.city + " - " + airport.icao,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}