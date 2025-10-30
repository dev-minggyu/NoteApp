package com.note.app.ui.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.note.app.ui.navigation.Screen

fun NavGraphBuilder.mainNavGraph(
    navigateToDetail: (Long?) -> Unit,
) {
    composable<Screen.Main> {
        MainScreen(
            onNavigateToDetail = navigateToDetail,
        )
    }
}