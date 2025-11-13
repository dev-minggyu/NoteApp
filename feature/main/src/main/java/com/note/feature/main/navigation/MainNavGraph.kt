package com.note.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.note.feature.common.navigation.Screen
import com.note.feature.main.MainScreen

fun NavGraphBuilder.mainNavGraph(
    navigateToDetail: (Long?) -> Unit,
) {
    composable<Screen.Main> {
        MainScreen(
            onNavigateToDetail = navigateToDetail,
        )
    }
}