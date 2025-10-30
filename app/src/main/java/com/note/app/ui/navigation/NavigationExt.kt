package com.note.app.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    navigate(Screen.Main, navOptions)
}

fun NavController.navigateToDetail(
    noteId: Long?,
    navOptions: NavOptions? = null
) {
    navigate(Screen.NoteDetail(noteId = noteId), navOptions)
}