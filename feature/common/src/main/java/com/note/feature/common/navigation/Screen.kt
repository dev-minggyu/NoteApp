package com.note.feature.common.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Main : Screen

    @Serializable
    data object Search : Screen

    @Serializable
    data class NoteDetail(
        val noteId: Long? = null,
    ) : Screen
}