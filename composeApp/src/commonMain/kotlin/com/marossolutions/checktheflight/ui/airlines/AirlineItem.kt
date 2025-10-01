package com.marossolutions.checktheflight.ui.airlines

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import checktheflight.composeapp.generated.resources.Res
import checktheflight.composeapp.generated.resources.error_icon
import com.marossolutions.domain.model.Airline
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun AirlineItem(airline: Airline, onItemClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(64.dp)
        ) {
            airline.logoUrl?.let { airlineLogoUrl ->
                when (val resource =
                    asyncPainterResource(airlineLogoUrl)) {
                    is Resource.Failure -> Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(Res.drawable.error_icon),
                        contentDescription = null,
                    )

                    is Resource.Loading,
                        -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                    is Resource.Success -> Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth,
                        painter = resource.value,
                        contentDescription = null,
                    )
                }
            } ?: Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.error_icon),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = airline.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
