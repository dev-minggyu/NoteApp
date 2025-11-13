package com.note.feature.main

import android.widget.Toast
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.note.feature.main.component.MainContent
import com.note.feature.main.component.MainFab
import com.note.feature.main.component.MainTopBar
import com.note.feature.main.viewmodel.MainViewModel
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.common.extension.HandleEvents
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
            MainTopBar(
                searchQuery = state.searchQuery,
                isGrid = state.isGrid,
                onSearchQueryChange = { query ->
                    viewModel.sendAction(MainContract.Action.SearchNote(query))
                },
                onToggleListMode = {
                    viewModel.sendAction(MainContract.Action.ToggleListMode)
                }
            )
        },
        floatingActionButton = {
            MainFab(
                onClick = {
                    viewModel.sendAction(MainContract.Action.NavigateToDetail(null))
                }
            )
        },
        containerColor = AppTheme.colors.contentBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        MainContent(
            state = state,
            paddingValues = paddingValues,
            onNoteClick = { note ->
                viewModel.sendAction(MainContract.Action.NavigateToDetail(note.id))
            }
        )
    }
}