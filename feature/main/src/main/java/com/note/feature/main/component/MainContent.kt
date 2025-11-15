package com.note.feature.main.component

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
import com.note.domain.model.Note
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.main.R
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
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = AppTheme.colors.primary
                )
            }

            notes.isEmpty() -> {
                Text(
                    text = stringResource(R.string.main_empty_note_list),
                    color = AppTheme.colors.emptyText,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)  // 추가
                )
            }

            else -> {
                NoteList(
                    notes = notes,
                    isGrid = isGrid,
                    onNoteClick = onNoteClick
                )
            }
        }
    }
}