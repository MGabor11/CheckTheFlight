package com.marossolutions.navigation

/**
 * Handles navigation events by updating the two-layer navigation state:
 *
 * - **Pre-top-level layer**: managed via [NavigationState.backStack] (the NavBackStack).
 *   This holds routes before the user enters bottom-bar navigation (e.g. ScreenWelcome).
 *
 * - **Top-level layer**: managed via [NavigationState.activeTopLevelRoute] and
 *   [NavigationState.subStacks]. Each bottom-bar tab has its own NavBackStack.
 */
class NavigatorImpl : Navigator {

    private var navigationState: NavigationState? = null

    override fun setNavigationState(state: NavigationState) {
        navigationState = state
    }

    /**
     * Navigate to a route.
     *
     * - If the route is a top-level destination, enter top-level mode and activate that tab.
     * - If already in top-level mode, push the route onto the current tab's sub-stack.
     * - Otherwise push onto the pre-top-level backstack.
     */
    override fun navigateTo(route: Route) {
        val state = navigationState ?: return

        if (route in state.topLevelRoutes) {
            // Enter (or switch to) top-level mode
            state.activeTopLevelRoute = route
        } else if (state.activeTopLevelRoute != null) {
            // Push onto the current tab's sub-stack
            val currentTab = state.activeTopLevelRoute!!
            state.subStacks[currentTab]?.add(route)
        } else {
            // Push onto the pre-top-level backstack
            state.backStack.add(route)
        }
    }

    /**
     * Navigate back.
     *
     * 1. If in top-level mode and the current tab has more than its root entry → pop it.
     * 2. If in top-level mode and the tab is at its root → exit top-level mode
     *    (return to the pre-top-level backstack, e.g. ScreenWelcome).
     * 3. If in pre-top-level mode → pop the pre-top-level backstack.
     *
     * @return true if navigation happened, false if already at the very first route.
     */
    override fun navigateBack(): Boolean {
        val state = navigationState ?: return false

        val topLevel = state.activeTopLevelRoute
        if (topLevel != null) {
            val tabStack = state.subStacks[topLevel]
            if (tabStack != null && tabStack.size > 1) {
                // Pop within the current tab
                tabStack.removeAt(tabStack.lastIndex)
                return true
            }
            // At tab root → exit top-level mode, go back to pre-top-level
            state.activeTopLevelRoute = null
            return state.backStack.isNotEmpty()
        }

        // Pre-top-level backstack
        if (state.backStack.size > 1) {
            state.backStack.removeLastOrNull()
            return true
        }
        return false
    }

    /**
     * Switch to a different top-level tab (bottom bar tap).
     * Preserves each tab's sub-stack so that switching back restores position.
     *
     * @param route the top-level route to switch to.
     * @throws IllegalArgumentException if the route is not a top-level route.
     */
    override fun switchTopLevel(route: Route) {
        val state = navigationState ?: return
        require(route in state.topLevelRoutes) {
            "Route $route is not a top-level route. Top-level routes are: ${state.topLevelRoutes}"
        }
        state.activeTopLevelRoute = route
    }
}