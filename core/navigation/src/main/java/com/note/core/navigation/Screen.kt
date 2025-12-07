package com.note.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Main : Screen

    @Serializable
    data object Setting : Screen

    @Serializable
    data class NoteDetail(
        val noteId: Long? = null,
    ) : Screen

    companion object {
        val startDestination = Main
        val bottomTabRoutes = listOf(
            Main,
            Setting
        )
    }
}