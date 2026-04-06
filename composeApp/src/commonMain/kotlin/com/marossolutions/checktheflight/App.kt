package com.marossolutions.checktheflight

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.marossolutions.checktheflight.navigation.MainMenuBottomAppBar
import com.marossolutions.checktheflight.navigation.NavigationRoot
import com.marossolutions.navigation.Navigator
import com.marossolutions.navigation.Route
import com.marossolutions.navigation.TOP_LEVEL_DESTINATIONS
import com.marossolutions.navigation.rememberNavigationState
import com.marossolutions.theme.CheckTheFlightTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

private const val BarSlideDurationMs = 500
private const val BarSlideDelayMs = 300
private val BottomBarHeight = 80.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    CheckTheFlightTheme {
        val navigationState = rememberNavigationState(
            startRoute = Route.ScreenWelcome,
            topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys.toSet()
        )
        val navigator = koinInject<Navigator>()
        LaunchedEffect(Unit) {
            navigator.setNavigationState(navigationState)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        navigationState.currentRoute?.titleRes?.let {
                            Text(stringResource(it))
                        }
                    },
                    navigationIcon = {
                        val showBackButton by remember {
                            derivedStateOf {
                                navigationState.canNavigateBack
                            }
                        }

                        if (showBackButton) {
                            IconButton(onClick = { navigator.navigateBack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            },
            bottomBar = {
                val showNavigationBar by remember {
                    derivedStateOf {
                        navigationState.isInTopLevel && navigationState.currentRoute in TOP_LEVEL_DESTINATIONS.keys
                    }
                }
                AnimatedBottomBar(
                    visible = showNavigationBar,
                    selectedKey = navigationState.currentTopLevelRoute,
                    onSelectKey =  navigator::switchTopLevel,
                )
            }
        ) { innerPadding ->
            NavigationRoot(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                onBackPress =  navigator::navigateBack,
                navigationState = navigationState
            )
        }
    }
}

@Composable
private fun AnimatedBottomBar(
    visible: Boolean,
    selectedKey: Route?,
    onSelectKey: (Route) -> Unit,
) {
    val systemBottomInsets = WindowInsets.navigationBars
        .asPaddingValues()
        .calculateBottomPadding()

    val totalBarHeight = BottomBarHeight + systemBottomInsets

    val targetValue = if (visible) {
        totalBarHeight
    } else {
        systemBottomInsets
    }

    val barHeight by animateDpAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = BarSlideDurationMs,
            delayMillis = BarSlideDelayMs,
        ),
    )

    if (barHeight > systemBottomInsets) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .clipToBounds(),
        ) {
            MainMenuBottomAppBar(
                modifier = Modifier.offset(y = totalBarHeight - barHeight),
                selectedKey = selectedKey,
                onSelectKey = onSelectKey,
            )
        }
    }
}
