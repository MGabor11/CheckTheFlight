/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marossolutions.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/**
 * Manages the overall navigation state with two layers:
 *
 * 1. **Pre-top-level backstack** – The underlying [backStack] from NavBackStack, holding routes
 *    that exist before the user enters top-level (bottom bar) navigation.
 *    Example: [ScreenWelcome].
 *
 * 2. **Top-level mode** – Once the user navigates to a top-level route, [activeTopLevelRoute]
 *    becomes non-null. Each top-level tab maintains its own [NavBackStack] via [subStacks].
 *    Back-pressing within a tab pops the tab's sub-stack; when that stack is down to its root,
 *    the next back press exits top-level mode and returns to the pre-top-level backstack.
 */
@Stable
class NavigationState(
    val backStack: NavBackStack<NavKey>,
    val subStacks: Map<Route, NavBackStack<NavKey>>,
    private val _activeTopLevelRoute: MutableState<Route?> = mutableStateOf<Route?>(null),
) {
    /**
     * The set of top-level routes, derived from [subStacks] keys.
     */
    val topLevelRoutes: Set<Route> = subStacks.keys

    /**
     * The currently active top-level route, or null if we are in the pre-top-level area.
     */
    var activeTopLevelRoute: Route?
        get() = _activeTopLevelRoute.value
        internal set(value) {
            _activeTopLevelRoute.value = value
        }

    /**
     * The route currently being displayed.
     */
    val currentRoute: Route? by derivedStateOf {
        val topLevel = activeTopLevelRoute
        if (topLevel != null) {
            subStacks[topLevel]?.lastOrNull() as? Route ?: topLevel
        } else {
            backStack.lastOrNull() as? Route
        }
    }

    /**
     * The current top-level route for bottom bar highlight purposes.
     * Returns the active top-level tab, or null when not in top-level mode.
     */
    val currentTopLevelRoute: Route? by derivedStateOf {
        activeTopLevelRoute
    }

    /**
     * Whether we are currently inside top-level (bottom bar) navigation.
     */
    val isInTopLevel: Boolean by derivedStateOf {
        activeTopLevelRoute != null
    }

    /**
     * Whether back navigation is possible.
     */
    val canNavigateBack: Boolean by derivedStateOf {
        val topLevel = activeTopLevelRoute
        if (topLevel != null) {
            val tabStack = subStacks[topLevel]
            // Can pop within tab, or can exit top-level back to pre-top-level
            (tabStack != null && tabStack.size > 1) || backStack.isNotEmpty()
        } else {
            backStack.size > 1
        }
    }

    /**
     * Builds the effective backstack to feed into NavDisplay.
     * Combines the pre-top-level backstack with the current tab's sub-stack.
     */
    val effectiveBackStack: List<NavKey> by derivedStateOf {
        val topLevel = activeTopLevelRoute
        if (topLevel != null) {
            val tabStack = subStacks[topLevel]?.toList() ?: listOf(topLevel)
            // Pre-top-level entries + current tab's entries
            backStack.toList() + tabStack
        } else {
            backStack.toList()
        }
    }
}

@Composable
fun rememberNavigationState(
    startRoute: Route,
    topLevelRoutes: Set<Route>,
): NavigationState {
    val backStack = rememberNavBackStack(serializersConfig, startRoute)
    val subStacks: Map<Route, NavBackStack<NavKey>> =
        topLevelRoutes.associateWith { route -> rememberNavBackStack(serializersConfig, route) }

    val activeTopLevelRoute = retain { mutableStateOf<Route?>(null) }

    return remember(startRoute, topLevelRoutes) {
        NavigationState(
            backStack = backStack,
            subStacks = subStacks,
            _activeTopLevelRoute = activeTopLevelRoute,
        )
    }
}

val serializersConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.ScreenWelcome::class, Route.ScreenWelcome.serializer())
            subclass(Route.ScreenTutorial::class, Route.ScreenTutorial.serializer())
            subclass(Route.ScreenHome::class, Route.ScreenHome.serializer())
            subclass(Route.ScreenAirports::class, Route.ScreenAirports.serializer())
            subclass(Route.ScreenAirportDetail::class, Route.ScreenAirportDetail.serializer())
            subclass(Route.ScreenAirlines::class, Route.ScreenAirlines.serializer())
            subclass(Route.ScreenAirlineDetail::class, Route.ScreenAirlineDetail.serializer())
        }
    }
}

@Composable
fun NavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>,
): SnapshotStateList<NavEntry<NavKey>> {
    val decorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
        rememberViewModelStoreNavEntryDecorator<NavKey>(),
    )
    val decoratedEntries = rememberDecoratedNavEntries(
        backStack = effectiveBackStack,
        entryDecorators = decorators,
        entryProvider = entryProvider,
    )

    return decoratedEntries.toMutableStateList()
}
