package com.note.app.ui.main

import com.note.app.base.contract.UiAction
import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState
import com.note.domain.model.Note

class MainContract {
    sealed interface Action : UiAction {
        data object ToggleListMode : Action
        data class DeleteNote(val note: Note) : Action
        data class NavigateToDetail(val noteId: Long?) : Action

        sealed interface Stream : Action {
            data object ObserveNotes : Action
        }
    }

    data class State(
        val notes: List<Note> = emptyList(),
        val isGrid: Boolean = false,
        val isLoading: Boolean = false,
        val error: Event.Error? = null
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
        data class NavigateToDetailMutation(val noteId: Long?) : Mutation
        data class ShowError(val error: Event.Error) : Mutation
    }
}