package com.note.app.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.note.core.navigation.AppNavigator
import com.note.core.navigation.Screen
import com.note.feature.main.navigation.mainNavGraph
import com.note.feature.notedetail.navigation.detailNavGraph
import com.note.feature.setting.navigation.settingNavGraph

@Composable
fun NoteApp() {
    val navController = rememberNavController()
    val navigator = remember(navController) { AppNavigator(navController) }

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(navigator = navigator)
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = Screen.startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            mainNavGraph(
                navigator = navigator,
            )

            detailNavGraph(
                navigator = navigator,
            )

            settingNavGraph(
                navigator = navigator
            )
        }
    }
}