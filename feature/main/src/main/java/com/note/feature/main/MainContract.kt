package com.note.feature.main

import com.note.feature.common.ui.base.contract.UiAction
import com.note.feature.common.ui.base.contract.UiEvent
import com.note.feature.common.ui.base.contract.UiMutation
import com.note.feature.common.ui.base.contract.UiState
import com.note.domain.model.Note
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class MainContract {
    sealed interface Action : UiAction {
        data object ToggleListMode : Action
        data class DeleteNote(val note: Note) : Action
        data class NavigateToDetail(val noteId: Long?) : Action
        data class SearchNote(val query: String) : Action

        sealed interface Stream : Action {
            data object ObserveNotes : Action
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val error: Event.Error? = null,
        val notes: ImmutableList<Note> = persistentListOf(),
        val isGrid: Boolean = false,
        val searchQuery: String = ""
    ) : UiState

    sealed interface Event : UiEvent {
        data class NavigateToDetail(val noteId: Long?) : Event
        data class ShowError(val error: Error) : Event

        sealed interface Error {
            data object InvalidNote : Error
            data object DeleteFailure : Error
        }
    }

    sealed interface Mutation : UiMutation {
        data object ShowProgress : Mutation
        data class NoteLoaded(val notes: List<Note>) : Mutation
        data object ToggleView : Mutation
        data class NoteDeleted(val note: Note) : Mutation
        data class UpdateSearchQuery(val searchQuery: String) : Mutation
        data class SearchResult(val notes: List<Note>, val searchQuery: String) : Mutation
        data class NavigateToDetailMutation(val noteId: Long?) : Mutation
        data class ShowError(val error: Event.Error) : Mutation
    }
}