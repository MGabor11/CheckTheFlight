package com.marossolutions.welcome.ui

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
import checktheflight.feature.welcome.generated.resources.Res
import checktheflight.feature.welcome.generated.resources.welcome_button
import com.marossolutions.theme.AppTheme
import com.marossolutions.welcome.viewmodel.TutorialViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TutorialScreen(viewModel: TutorialViewModel = koinViewModel()) {

    TutorialScreenContent(
        navigateToNextScreen = viewModel::navigateToNextScreen
    )
}

@Composable
private fun TutorialScreenContent(navigateToNextScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("TUTORIAL", fontSize = 32.sp)

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
        TutorialScreenContent {}
    }
}