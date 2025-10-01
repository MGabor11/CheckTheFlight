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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.marossolutions.checktheflight.navigation.AppScreen
import com.marossolutions.checktheflight.navigation.NavigationEvent
import com.marossolutions.checktheflight.navigation.NavigationRoot
import com.marossolutions.checktheflight.navigation.SimpleNavigator
import com.marossolutions.checktheflight.navigation.TopLevelBackStack
import com.marossolutions.checktheflight.theme.AppTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    AppTheme {
        val simpleNavigator = koinInject<SimpleNavigator>()
        val topLevelBackStack = koinInject<TopLevelBackStack<AppScreen>>()

        LaunchedEffect(Unit) {
            simpleNavigator.navigationEvents
                .onEach { navigateEvent ->
                    when (navigateEvent) {
                        is NavigationEvent.ForwardNavigation -> {
                            val (screen, navigationOptions) = navigateEvent
                            if (screen is AppScreen.BottomNavScreen) {
                                topLevelBackStack.switchTopLevel(screen)
                            } else {
                                topLevelBackStack.add(screen, navigationOptions)
                            }
                        }

                        NavigationEvent.NavigateUp -> topLevelBackStack.removeLast()
                    }
                }
                .launchIn(this)
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
                                topLevelBackStack.backStack.lastOrNull()?.title ?: ""
                            }
                        }
                        Text(title)
                    },
                    navigationIcon = {
                        val showBackButton by remember {
                            derivedStateOf {
                                topLevelBackStack.backStack.size > 1
                            }
                        }

                        if (showBackButton) {
                            IconButton(onClick = { simpleNavigator.navigateUp() }) {
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
                        topLevelBackStack.backStack.lastOrNull() is AppScreen.BottomNavScreen
                    }
                }

                val bottomNavItems = listOf(
                    AppScreen.BottomNavScreen.ScreenHome,
                    AppScreen.BottomNavScreen.ScreenAirports,
                    AppScreen.BottomNavScreen.ScreenAirlines
                )
                AnimatedVisibility(showShowNavigationBar) {
                    NavigationBar {
                        bottomNavItems.forEach { item ->
                            val selected = topLevelBackStack.topLevelKey == item
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    topLevelBackStack.switchTopLevel(item)
                                },
                                icon = {
                                    Icon(
                                        imageVector = item.navIcon,
                                        contentDescription = item.navTitle
                                    )
                                },
                                label = {
                                    Text(item.navTitle)
                                },
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavigationRoot(
                simpleNavigator = simpleNavigator,
                innerPadding = innerPadding,
                topLevelBackStack = topLevelBackStack,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}