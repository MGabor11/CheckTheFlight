package com.marossolutions.checktheflight.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.marossolutions.navigation.BottomNavItem
import com.marossolutions.navigation.Route
import com.marossolutions.navigation.TOP_LEVEL_DESTINATIONS
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainMenuBottomAppBar(
    selectedKey: Route?,
    onSelectKey: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { (topLevelDestination: Route, data: BottomNavItem) ->
            val title = stringResource(data.titleRes)
            NavigationBarItem(
                selected = topLevelDestination == selectedKey,
                onClick = {
                    onSelectKey(topLevelDestination)
                },
                icon = {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = title
                    )
                },
                label = {
                    Text(title)
                }
            )
        }
    }
}
