package com.note.app.ui.main

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.note.app.R
import com.note.app.ui.component.SimpleTextField
import com.note.app.ui.theme.AppTheme
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
    val toggleButtonDegree by animateFloatAsState(
        targetValue = if (state.isGrid) 90f else 0f
    )

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
                    SimpleTextField(
                        value = state.searchQuery,
                        onValueChange = { query ->
                            viewModel.sendAction(MainContract.Action.SearchNote(query = query))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search",
                                tint = AppTheme.colors.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        trailingIcon = {
                            if (state.searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        viewModel.sendAction(MainContract.Action.SearchNote(query = ""))
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "search_clear",
                                        tint = AppTheme.colors.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.sendAction(MainContract.Action.ToggleListMode) }) {
                        Icon(
                            modifier = Modifier.rotate(toggleButtonDegree),
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(R.string.main_change_note_list_layout),
                            tint = AppTheme.colors.toggleTint,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colors.contentBackground
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.sendAction(MainContract.Action.NavigateToDetail(null)) },
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
        },
        containerColor = AppTheme.colors.contentBackground,
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
                        color = AppTheme.colors.primary
                    )
                }

                state.notes.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.main_empty_note_list),
                        modifier = Modifier.align(Alignment.Center),
                        color = AppTheme.colors.emptyText,
                        fontSize = 16.sp
                    )
                }

                else -> {
                    NoteList(
                        notes = state.notes,
                        isGrid = state.isGrid,
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
    isGrid: Boolean,
    onNoteClick: (Note) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(if (isGrid) 2 else 1),
        modifier = Modifier.fillMaxSize(),
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
                    onClick = { onNoteClick(note) },
                )
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
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.contentBackground),
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
                color = AppTheme.colors.titleText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                fontSize = 12.sp,
                color = AppTheme.colors.subTitleText,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}