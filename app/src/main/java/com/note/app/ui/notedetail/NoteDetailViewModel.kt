package com.note.app.ui.notedetail

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow

class NoteDetailViewModel(
    private val processor: NoteDetailProcessor,
    private val reducer: NoteDetailReducer
) : BaseViewModel<NoteDetailContract.Action, NoteDetailContract.Mutation, NoteDetailContract.State, NoteDetailContract.Event>(
    processor = processor,
    reducer = reducer
) {
    override val uiState: StateFlow<NoteDetailContract.State> = uiEvent
        .reduceToState(
            processor = ::processEvent,
            reducer = ::reduceMutation,
            initialState = NoteDetailContract.State(),
            scope = viewModelScope
        )

    fun loadNote(noteId: Long?) {
        noteId?.let {
            sendEvent(NoteDetailContract.Action.LoadNote(it))
        }
    }
}