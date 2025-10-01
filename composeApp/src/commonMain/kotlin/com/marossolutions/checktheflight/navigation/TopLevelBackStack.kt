package com.marossolutions.checktheflight.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList

class TopLevelBackStack<T : AppScreen>(private val startKey: T) {

    private var topLevelBackStacks: HashMap<T, SnapshotStateList<T>> = hashMapOf(
        startKey to mutableStateListOf(startKey)
    )

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() {
        backStack.clear()
        val currentStack = topLevelBackStacks[topLevelKey] ?: emptyList()

        if (topLevelKey == startKey) {
            backStack.addAll(currentStack)
        } else {
            val startStack = topLevelBackStacks[startKey] ?: emptyList()
            backStack.addAll(startStack + currentStack)
        }
    }

    fun switchTopLevel(key: T) {
        topLevelBackStacks.entries.removeAll { it.key is AppScreen.BottomNavScreen && key != topLevelKey }

        if (topLevelBackStacks[key] == null) {
            topLevelBackStacks[key] = mutableStateListOf(key)
        }

        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T, navigationOptions: NavigationOptions?) {
        navigationOptions?.let { options ->
            val (popUpToScreen, popUpToInclusive) = options
            if (topLevelBackStacks[startKey]?.contains(popUpToScreen) ?: false) {
                topLevelBackStacks.entries.removeAll { it.key is AppScreen.BottomNavScreen}
                topLevelKey = startKey
            }

            val currentStack = topLevelBackStacks[topLevelKey] ?: return

            val index = currentStack.indexOf(popUpToScreen)

            val newStack = if (popUpToInclusive) {
                currentStack.subList(0, index) // remove popUpTo as well
            } else {
                currentStack.subList(0, index + 1) // keep popUpTo
            }

            // Replace stack with trimmed list + the new element
            topLevelBackStacks[topLevelKey] = mutableStateListOf<T>().apply {
                addAll(newStack)
                add(key)
            }
        } ?: topLevelBackStacks[topLevelKey]?.add(key)

        updateBackStack()
    }

    fun removeLast() {
        val currentStack = topLevelBackStacks[topLevelKey] ?: return

        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        } else if (topLevelKey != startKey) {
            topLevelBackStacks.entries.removeAll { it.key is AppScreen.BottomNavScreen }
            topLevelKey = startKey
        }
        updateBackStack()
    }

    fun replaceStack(vararg keys: T) {
        topLevelBackStacks[topLevelKey] = mutableStateListOf(*keys)
        updateBackStack()
    }
}
