package com.note.feature.notedetail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.note.core.navigation.DeepLink
import com.note.feature.common.extension.slideInLeft
import com.note.feature.common.extension.slideOutRight
import com.note.core.navigation.Screen
import com.note.feature.notedetail.NoteDetailScreen

fun NavGraphBuilder.detailNavGraph(
    popBackStack: () -> Unit,
) {
    composable<Screen.NoteDetail>(
        enterTransition = { slideInLeft(durationMillis = 1000) },
        popExitTransition = { slideOutRight(durationMillis = 1000) },
        deepLinks = listOf(
            navDeepLink<Screen.NoteDetail>(
                basePath = DeepLink.NoteDetail.BASE_PATH
            )
        )
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<Screen.NoteDetail>()
        NoteDetailScreen(
            noteId = args.noteId,
            onNavigateBack = popBackStack,
        )
    }
}