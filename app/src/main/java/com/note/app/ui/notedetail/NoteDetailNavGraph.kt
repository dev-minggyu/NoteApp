package com.note.app.ui.notedetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.note.app.ui.navigation.Screen
import com.note.app.utils.extension.slideInLeft
import com.note.app.utils.extension.slideOutRight

fun NavGraphBuilder.detailNavGraph(
    popBackStack: () -> Unit,
) {
    composable<Screen.NoteDetail>(
        enterTransition = { slideInLeft(durationMillis = 1000) },
        popExitTransition = { slideOutRight(durationMillis = 1000) }
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<Screen.NoteDetail>()
        NoteDetailScreen(
            noteId = args.noteId,
            onNavigateBack = popBackStack,
        )
    }
}