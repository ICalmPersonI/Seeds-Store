package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.calmperson.seedsstore.ui.screen.Screen

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentDestination: NavDestination?,
    screens: List<Screen>
) {
    NavigationBar(modifier = modifier) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(screen.iconResId),
                        contentDescription = null
                    )
                },
                label = {  },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route)
                }
            )
        }
    }

}