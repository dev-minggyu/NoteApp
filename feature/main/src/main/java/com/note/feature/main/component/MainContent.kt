package com.note.feature.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.note.domain.model.Note
import com.note.feature.common.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MainContent(
    notes: ImmutableList<Note>,
    isLoading: Boolean,
    isGrid: Boolean,
    paddingValues: PaddingValues,
    onNoteClick: (Note) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AppTheme.colors.primary
            )
        } else {
            NoteList(
                notes = notes,
                isGrid = isGrid,
                onNoteClick = onNoteClick
            )
        }
    }
}