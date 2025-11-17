package com.note.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.note.app.R
import com.note.feature.common.navigation.Screen

sealed class BottomNavItem(
    val route: Any,
    val icon: ImageVector,
    @StringRes val labelResId: Int
) {
    object Notes : BottomNavItem(Screen.Main, Icons.Default.Home, R.string.bottom_navi_label_main)
    object Setting : BottomNavItem(Screen.Setting, Icons.Default.Settings, R.string.bottom_navi_label_setting)
}

@Composable
fun AppBottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Notes,
        BottomNavItem.Setting,
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF70C4A3)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.labelResId)
                    )
                },
                label = { Text(stringResource(item.labelResId)) },
                selected = currentDestination?.hasRoute(item.route::class) == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF70C4A3),
                    selectedTextColor = Color(0xFF70C4A3),
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                    indicatorColor = Color(0xFFE8F5F0)
                )
            )
        }
    }
}