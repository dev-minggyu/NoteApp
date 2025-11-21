package com.note.feature.main.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.note.domain.model.Note
import com.note.feature.common.extension.dateFormat
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.main.R
import kotlinx.collections.immutable.ImmutableList
import java.util.Date

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: ImmutableList<Note>,
    isGrid: Boolean,
    onNoteClick: (Note) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(if (isGrid) 2 else 1),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp
    ) {
        items(
            items = notes,
            key = { it.id }
        ) { note ->
            Box(modifier = Modifier.animateItem()) {
                NoteListItem(
                    note = note,
                    onClick = { onNoteClick(note) }
                )
            }
        }
    }
}

@Composable
fun NoteListItem(
    note: Note,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.contentBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            NoteTitle(note.title)
            Spacer(modifier = Modifier.height(8.dp))
            NoteContent(note.content)
            if (note.alarmTime > 0L) {
                Spacer(modifier = Modifier.height(8.dp))
                NoteAlarmInfo(
                    alarmTime = note.alarmTime,
                    isEnabled = note.isAlarmEnabled
                )
            }
        }
    }
}

@Composable
private fun NoteTitle(title: String) {
    Text(
        text = title.ifBlank { stringResource(R.string.main_empty_title) },
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.titleText,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun NoteContent(content: String) {
    Text(
        text = content,
        fontSize = 12.sp,
        color = AppTheme.colors.subTitleText,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun NoteAlarmInfo(
    modifier: Modifier = Modifier,
    alarmTime: Long,
    isEnabled: Boolean
) {
    val alarmText = remember(alarmTime) {
        Date(alarmTime).dateFormat("MM/dd HH:mm")
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = if (isEnabled) Icons.Default.Notifications else Icons.Default.NotificationsOff,
            contentDescription = null,
            tint = if (isEnabled) AppTheme.colors.primary else AppTheme.colors.disable,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = alarmText,
            fontSize = 11.sp,
            color = if (isEnabled) {
                AppTheme.colors.subTitleText
            } else {
                AppTheme.colors.disable
            }
        )
    }
}