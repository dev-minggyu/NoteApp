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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import com.note.app.R
import com.note.core.navigation.AppNavigator
import com.note.core.navigation.Screen
import com.note.feature.common.ui.theme.AppTheme

enum class BottomNavItem(
    val route: Screen,
    val icon: ImageVector,
    @get:StringRes val labelResId: Int
) {
    NOTES(Screen.Main, Icons.Default.Home, R.string.bottom_navi_label_main),
    SETTING(Screen.Setting, Icons.Default.Settings, R.string.bottom_navi_label_setting);
}

@Composable
fun AppBottomNavigationBar(navigator: AppNavigator) {
    NavigationBar(
        containerColor = AppTheme.colors.background,
        contentColor = AppTheme.colors.iconSecondary
    ) {
        val navBackStackEntry by navigator.currentBackStackEntry.collectAsState(initial = null)
        val currentDestination = navBackStackEntry?.destination

        BottomNavItem.entries.forEach { item ->
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
                    navigator.navigateToBottomBarRoute(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colors.primary,
                    selectedTextColor = AppTheme.colors.primary,
                    unselectedIconColor = AppTheme.colors.iconSecondary,
                    unselectedTextColor = AppTheme.colors.iconSecondary,
                    indicatorColor = AppTheme.colors.primaryContainer
                )
            )
        }
    }
}