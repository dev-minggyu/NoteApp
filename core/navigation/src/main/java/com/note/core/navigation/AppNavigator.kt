package com.note.core.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.Flow

class AppNavigator(val navController: NavController) {
    val currentBackStackEntry: Flow<NavBackStackEntry>
        get() = navController.currentBackStackEntryFlow

    val currentDestination: NavDestination?
        get() = navController.currentDestination

    private val currentBottomTab: Screen?
        get() {
            return navController.currentBackStack.value
                .asReversed()
                .firstNotNullOfOrNull { entry ->
                    Screen.bottomTabRoutes.firstOrNull { tab ->
                        entry.destination.hasRoute(tab::class)
                    }
                }
        }

    fun navigateToMain(navOptions: NavOptions? = null) {
        navController.navigate(Screen.Main, navOptions)
    }

    fun navigateToDetail(
        noteId: Long?,
        navOptions: NavOptions? = null
    ) {
        navController.navigate(Screen.NoteDetail(noteId = noteId), navOptions)
    }

    fun navigateToSetting(navOptions: NavOptions? = null) {
        navController.navigate(Screen.Setting, navOptions)
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateToBottomBarRoute(route: Screen) {
        if (currentBottomTab == route) {
            // 동일한 탭 클릭 시 하위 스택 pop
            navController.popBackStack(route, inclusive = false)
        } else {
            val navOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            navController.navigate(route, navOptions)
        }
    }
}
