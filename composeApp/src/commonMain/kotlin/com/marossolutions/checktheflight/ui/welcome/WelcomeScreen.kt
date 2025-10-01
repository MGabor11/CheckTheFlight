package com.marossolutions.checktheflight.ui.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import checktheflight.composeapp.generated.resources.Res
import checktheflight.composeapp.generated.resources.welcome_button
import checktheflight.composeapp.generated.resources.welcome_title
import com.marossolutions.checktheflight.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WelcomeScreen(viewModel: WelcomeViewModel = koinViewModel()) {

    WelcomeScreenContent(
        navigateToNextScreen = viewModel::navigateToNextScreen
    )
}

@Composable
private fun WelcomeScreenContent(navigateToNextScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(Res.string.welcome_title), fontSize = 32.sp)

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = navigateToNextScreen) {
            Text(stringResource(Res.string.welcome_button))
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenContentPreview() {
    AppTheme {
        WelcomeScreenContent {}
    }
}
