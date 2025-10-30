package com.note.app.ui.notedetail

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.note.app.R
import com.note.app.ui.theme.AppColors
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: NoteDetailViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
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
                            Toast.makeText(context, context.getString(R.string.note_detail_fail_load_note), Toast.LENGTH_SHORT).show()
                        }

                        NoteDetailContract.Event.Error.RequireContent -> {
                            Toast.makeText(context, context.getString(R.string.note_detail_require_content), Toast.LENGTH_SHORT).show()
                        }

                        NoteDetailContract.Event.Error.SaveFailure -> {
                            Toast.makeText(context, context.getString(R.string.note_detail_fail_save_note), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (noteId == null) stringResource(R.string.note_detail_title_new) else stringResource(R.string.note_detail_title_edit),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.titleText
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.sendAction(NoteDetailContract.Action.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.note_detail_back),
                            tint = AppColors.titleText
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.sendAction(NoteDetailContract.Action.SaveNote) },
                        enabled = !state.isSaving
                    ) {
                        if (state.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = AppColors.primary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.note_detail_save),
                                tint = AppColors.primary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.contentBackground
                )
            )
        },
        containerColor = AppColors.contentBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = AppColors.primary
                )
            } else {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.sendAction(NoteDetailContract.Action.UpdateTitle(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.note_detail_placeholder_title), color = AppColors.emptyText) },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.titleText
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.primary,
                        unfocusedBorderColor = AppColors.emptyText
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.content,
                    onValueChange = { viewModel.sendAction(NoteDetailContract.Action.UpdateContent(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    placeholder = { Text(stringResource(R.string.note_detail_placeholder_content), color = AppColors.emptyText) },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = AppColors.titleText
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.primary,
                        unfocusedBorderColor = AppColors.emptyText
                    )
                )
            }
        }
    }
}