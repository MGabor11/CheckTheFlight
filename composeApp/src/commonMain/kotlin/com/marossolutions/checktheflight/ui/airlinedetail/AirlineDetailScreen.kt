package com.marossolutions.checktheflight.ui.airlinedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import checktheflight.composeapp.generated.resources.Res
import checktheflight.composeapp.generated.resources.error_icon
import com.marossolutions.checktheflight.ui.FullScreenLoading
import com.marossolutions.domain.model.Airline
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun AirlineDetailScreen(viewModel: AirlineDetailViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    AirlineDetailScreenContent(
        uiState = uiState,
    )
}

@Composable
private fun AirlineDetailScreenContent(
    uiState: AirlineDetailViewModel.AirlineDetailUiState,
) {
    when (uiState) {
        is AirlineDetailViewModel.AirlineDetailUiState.Content -> Content(
            airline = uiState.airline
        )

        AirlineDetailViewModel.AirlineDetailUiState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun Content(
    airline: Airline
) {
    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.size(64.dp)
        ) {
            airline.logoUrl?.let { airlineLogoUrl ->
                when (val resource = asyncPainterResource(airlineLogoUrl)) {
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

        Text(
            text = airline.name,
        )
    }
}
