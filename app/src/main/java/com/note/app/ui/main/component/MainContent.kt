package com.note.app.ui.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.note.app.R
import com.note.app.ui.main.MainContract
import com.note.app.ui.theme.AppTheme
import com.note.domain.model.Note

@Composable
fun MainContent(
    state: MainContract.State,
    paddingValues: PaddingValues,
    onNoteClick: (Note) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = AppTheme.colors.primary
                )
            }

            state.notes.isEmpty() -> {
                Text(
                    text = stringResource(R.string.main_empty_note_list),
                    color = AppTheme.colors.emptyText,
                    fontSize = 16.sp
                )
            }

            else -> {
                NoteList(
                    notes = state.notes,
                    isGrid = state.isGrid,
                    onNoteClick = onNoteClick
                )
            }
        }
    }
}