package com.note.app.ui.main.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.note.app.R
import com.note.app.ui.theme.AppTheme

@Composable
fun MainFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = AppTheme.colors.primary,
        shape = CircleShape,
        modifier = Modifier.size(56.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.main_add_note),
            tint = AppTheme.colors.fabTint
        )
    }
}