package com.note.core.navigation

import android.net.Uri

object DeepLink {
    private const val SCHEME = "note"

    object NoteDetail {
        private const val HOST = "detail"
        const val BASE_PATH = "$SCHEME://$HOST"
        const val QUERY_NOTE_ID = "noteId"

        fun uri(noteId: Long): Uri = Uri.Builder()
            .scheme(SCHEME)
            .authority(HOST)
            .appendQueryParameter(QUERY_NOTE_ID, noteId.toString())
            .build()
    }
}