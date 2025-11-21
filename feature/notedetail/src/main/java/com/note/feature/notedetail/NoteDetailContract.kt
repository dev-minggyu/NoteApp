package com.note.feature.notedetail

import com.note.domain.model.Note
import com.note.feature.common.ui.base.contract.UiAction
import com.note.feature.common.ui.base.contract.UiEvent
import com.note.feature.common.ui.base.contract.UiMutation
import com.note.feature.common.ui.base.contract.UiState

class NoteDetailContract {
    sealed interface Action : UiAction {
        data class LoadNote(val noteId: Int?) : Action
        data class UpdateTitle(val title: String) : Action
        data class UpdateContent(val content: String) : Action
        data object SaveNote : Action
        data object NavigateBack : Action
    }

    data class State(
        val note: Note? = null,
        val title: String = "",
        val content: String = "",
        val isLoading: Boolean = false,
        val isSaving: Boolean = false,
        val isAlarmEnabled: Boolean = false,
        val alarmTime: Long? = null,
        val alarmMessage: String? = null,
        val error: Event.Error? = null
    ) : UiState

    sealed interface Event : UiEvent {
        data object NavigateBack : Event
        data object NoteSaved : Event
        data class ShowError(val error: Error) : Event
        sealed interface Error {
            data object InvalidNote : Error
            data object RequireContent : Error
            data object SaveFailure : Error
        }
    }

    sealed interface Mutation : UiMutation {
        data object ShowProgress : Mutation
        data class NoteLoaded(val note: Note) : Mutation
        data class TitleUpdated(val title: String) : Mutation
        data class ContentUpdated(val content: String) : Mutation
        data object SavingNote : Mutation
        data object NoteSavedSuccess : Mutation
        data object NavigateBackMutation : Mutation
        data class ShowError(val error: Event.Error) : Mutation
    }
}