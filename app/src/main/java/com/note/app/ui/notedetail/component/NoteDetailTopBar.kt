package com.note.app.ui.notedetail.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.note.app.R
import com.note.app.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailTopBar(
    isNewNote: Boolean,
    isSaving: Boolean,
    onNavigateBack: () -> Unit,
    onSaveNote: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = if (isNewNote) {
                    stringResource(R.string.note_detail_title_new)
                } else {
                    stringResource(R.string.note_detail_title_edit)
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.titleText
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.note_detail_back),
                    tint = AppTheme.colors.titleText
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSaveNote,
                enabled = !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = AppTheme.colors.primary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.note_detail_save),
                        tint = AppTheme.colors.primary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.contentBackground
        )
    )
}