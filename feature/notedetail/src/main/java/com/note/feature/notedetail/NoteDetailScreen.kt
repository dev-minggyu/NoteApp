package com.note.feature.notedetail

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.notedetail.component.NoteDetailContent
import com.note.feature.notedetail.component.NoteDetailTopBar
import com.note.feature.notedetail.viewmodel.NoteDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteDetailScreen(
    noteId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: NoteDetailViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(noteId) {
        noteId?.let {
            viewModel.sendAction(NoteDetailContract.Action.LoadNote(noteId))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is NoteDetailContract.Event.NavigateBack -> {
                    onNavigateBack()
                }

                is NoteDetailContract.Event.NoteSaved -> {
                    onNavigateBack()
                }

                is NoteDetailContract.Event.ShowError -> {
                    when (event.error) {
                        NoteDetailContract.Event.Error.InvalidNote -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.note_detail_fail_load_note),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        NoteDetailContract.Event.Error.RequireContent -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.note_detail_require_content),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        NoteDetailContract.Event.Error.SaveFailure -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.note_detail_fail_save_note),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            NoteDetailTopBar(
                isNewNote = noteId == null,
                isSaving = state.isSaving,
                onNavigateBack = { viewModel.sendAction(NoteDetailContract.Action.NavigateBack) },
                onSaveNote = { viewModel.sendAction(NoteDetailContract.Action.SaveNote) }
            )
        },
        containerColor = AppTheme.colors.contentBackground
    ) { paddingValues ->
        NoteDetailContent(
            isLoading = state.isLoading,
            title = state.title,
            content = state.content,
            onTitleChange = { viewModel.sendAction(NoteDetailContract.Action.UpdateTitle(it)) },
            onContentChange = { viewModel.sendAction(NoteDetailContract.Action.UpdateContent(it)) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}