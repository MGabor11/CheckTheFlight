package com.marossolutions.checktheflight.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.collections.set

private val startScreen = AppScreen.ScreenWelcome

class SimpleNavigatorImpl : SimpleNavigator {

    private var topLevelBackStacks: HashMap<AppScreen, SnapshotStateList<AppScreen>> = hashMapOf(
        startScreen to mutableStateListOf(startScreen)
    )

    private var _topLevelScreen: AppScreen by mutableStateOf(startScreen)

    override val backStack: SnapshotStateList<AppScreen> = mutableStateListOf(startScreen)

    override fun getTopLevelScreen(): AppScreen = _topLevelScreen

    override fun navigateTo(screen: AppScreen, navigationOptions: NavigationOptions?) {
        if (screen is AppScreen.BottomNavScreen) {
            switchTopLevelBackStack(screen)
        } else {
            add(screen, navigationOptions)
        }
    }

    override fun navigateUp() {
        val currentStack = topLevelBackStacks[_topLevelScreen] ?: return

        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        } else if (_topLevelScreen != startScreen) {
            topLevelBackStacks.entries.removeAll { it.key is AppScreen.BottomNavScreen }
            _topLevelScreen = startScreen
        }
        updateBackStack()
    }

    private fun updateBackStack() {
        backStack.clear()
        val currentStack = topLevelBackStacks[_topLevelScreen] ?: emptyList()

        if (_topLevelScreen == startScreen) {
            backStack.addAll(currentStack)
        } else {
            val startStack = topLevelBackStacks[startScreen] ?: emptyList()
            backStack.addAll(startStack + currentStack)
        }
    }

    private fun switchTopLevelBackStack(screen: AppScreen) {
        topLevelBackStacks.entries.removeAll { it.key is AppScreen.BottomNavScreen && screen != _topLevelScreen }

        if (topLevelBackStacks[screen] == null) {
            topLevelBackStacks[screen] = mutableStateListOf(screen)
        }

        _topLevelScreen = screen
        updateBackStack()
    }

    private fun add(screen: AppScreen, navigationOptions: NavigationOptions?) {
        navigationOptions?.let { options ->
            val (popUpToScreen, popUpToInclusive) = options
            if (topLevelBackStacks[startScreen]?.contains(popUpToScreen) ?: false) {
                topLevelBackStacks.entries.removeAll { it.key is AppScreen.BottomNavScreen }
                _topLevelScreen = startScreen
            }

            val currentStack = topLevelBackStacks[_topLevelScreen] ?: return

            val index = currentStack.indexOf(popUpToScreen)

            val newStack = if (popUpToInclusive) {
                currentStack.subList(0, index) // remove popUpTo as well
            } else {
                currentStack.subList(0, index + 1) // keep popUpTo
            }

            // Replace stack with trimmed list + the new element
            topLevelBackStacks[_topLevelScreen] = mutableStateListOf<AppScreen>().apply {
                addAll(newStack)
                add(screen)
            }
        } ?: topLevelBackStacks[_topLevelScreen]?.add(screen)

        updateBackStack()
    }
}
