package com.note.app.ui.notedetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.note.app.ui.navigation.Screen

fun NavGraphBuilder.detailNavGraph(
    popBackStack: () -> Unit,
) {
    composable<Screen.NoteDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<Screen.NoteDetail>()
        NoteDetailScreen(
            noteId = args.noteId,
            onNavigateBack = popBackStack,
        )
    }
}