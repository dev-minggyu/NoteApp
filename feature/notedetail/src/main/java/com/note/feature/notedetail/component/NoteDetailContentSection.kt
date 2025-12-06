package com.note.feature.notedetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.notedetail.R

@Composable
fun NoteDetailContentSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp),
                color = AppTheme.colors.primary
            )
        } else {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = onTitleChange,
                placeholder = {
                    Text(
                        text = stringResource(R.string.note_detail_placeholder_title),
                        color = AppTheme.colors.textTertiary,
                        style = AppTheme.typo.titleLarge
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    color = AppTheme.colors.textPrimary
                ).merge(AppTheme.typo.titleLarge),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppTheme.colors.primary,
                    unfocusedBorderColor = AppTheme.colors.border
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = content,
                onValueChange = onContentChange,
                placeholder = {
                    Text(
                        text = stringResource(R.string.note_detail_placeholder_content),
                        color = AppTheme.colors.textTertiary,
                        style = AppTheme.typo.bodyLarge
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    color = AppTheme.colors.textPrimary
                ).merge(AppTheme.typo.bodyLarge),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppTheme.colors.primary,
                    unfocusedBorderColor = AppTheme.colors.border
                )
            )
        }
    }
}