package com.note.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.note.core.navigation.Screen
import com.note.core.navigation.navigateToDetail
import com.note.feature.main.navigation.mainNavGraph
import com.note.feature.notedetail.navigation.detailNavGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Main,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            mainNavGraph(
                navigateToDetail = navController::navigateToDetail,
            )

            detailNavGraph(
                popBackStack = navController::popBackStack,
            )
        }
    }
}