package com.note.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.note.app.ui.main.mainNavGraph
import com.note.app.ui.notedetail.detailNavGraph

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