package com.marossolutions.checktheflight

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
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
import com.marossolutions.checktheflight.navigation.HomeNavigationBar
import com.marossolutions.checktheflight.navigation.NavigationRoot
import com.marossolutions.navigation.Navigator
import com.marossolutions.navigation.Route
import com.marossolutions.navigation.TOP_LEVEL_DESTINATIONS
import com.marossolutions.navigation.rememberNavigationState
import com.marossolutions.theme.AppTheme
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    AppTheme {
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        val title by remember {
                            derivedStateOf {
                                navigationState.currentRoute?.title ?: ""
                            }
                        }
                        Text(title)
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
                val showShowNavigationBar by remember {
                    derivedStateOf {
                        navigationState.isInTopLevel
                    }
                }

                AnimatedVisibility(showShowNavigationBar) {
                    HomeNavigationBar(
                        selectedKey = navigationState.currentTopLevelRoute,
                        onSelectKey = {
                            navigator.switchTopLevel(it)
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavigationRoot(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                onBackPress = { navigator.navigateBack() },
                navigationState = navigationState
            )
        }
    }
}
