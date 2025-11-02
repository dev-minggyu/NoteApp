package com.note.app.ui.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.note.app.R
import com.note.app.ui.theme.AppColors
import com.note.app.utils.extension.HandleEvents
import com.note.domain.model.Note
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToDetail: (Long?) -> Unit,
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    HandleEvents(viewModel.uiEvent) { event ->
        when (event) {
            is MainContract.Event.NavigateToDetail -> {
                onNavigateToDetail(event.noteId)
            }

            is MainContract.Event.ShowError -> {
                when (event.error) {
                    MainContract.Event.Error.InvalidNote -> {
                        Toast.makeText(context, context.getString(R.string.main_fail_load_note), Toast.LENGTH_SHORT).show()
                    }

                    MainContract.Event.Error.DeleteFailure -> {
                        Toast.makeText(context, context.getString(R.string.main_fail_delete_note), Toast.LENGTH_SHORT).show()
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
                        text = stringResource(R.string.main_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.titleText
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.sendAction(MainContract.Action.ToggleListMode) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(R.string.main_change_note_list_layout),
                            tint = AppColors.toggleTint,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.contentBackground
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.sendAction(MainContract.Action.NavigateToDetail(null)) },
                containerColor = AppColors.primary,
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.main_add_note),
                    tint = AppColors.fabTint
                )
            }
        },
        containerColor = AppColors.contentBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppColors.primary
                    )
                }

                state.notes.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.main_empty_note_list),
                        modifier = Modifier.align(Alignment.Center),
                        color = AppColors.emptyText,
                        fontSize = 16.sp
                    )
                }

                else -> {
                    NoteList(
                        notes = state.notes,
                        isGridView = state.isGridView,
                        onNoteClick = { note ->
                            viewModel.sendAction(MainContract.Action.NavigateToDetail(note.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteList(
    notes: List<Note>,
    isGridView: Boolean,
    onNoteClick: (Note) -> Unit
) {
    if (isGridView) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalItemSpacing = 12.dp
        ) {
            items(
                items = notes,
                key = { it.id }
            ) { note ->
                NoteGridItem(note = note, onClick = { onNoteClick(note) })
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = notes, key = { it.id }) { note ->
                NoteListItem(note = note, onClick = { onNoteClick(note) })
            }
        }
    }
}

@Composable
fun NoteListItem(
    note: Note,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.contentBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title.ifBlank { stringResource(R.string.main_empty_title) },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.titleText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                fontSize = 14.sp,
                color = AppColors.subTitleText,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun NoteGridItem(
    note: Note,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.contentBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = note.title.ifBlank { stringResource(R.string.main_empty_title) },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.titleText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                fontSize = 12.sp,
                color = AppColors.subTitleText,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}