package com.note.app.ui.notedetail

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow

class NoteDetailViewModel(
    private val processor: NoteDetailProcessor,
    private val reducer: NoteDetailReducer
) : BaseViewModel<NoteDetailContract.State, NoteDetailContract.Action, NoteDetailContract.Event, NoteDetailContract.Mutation>(
    processor = processor,
    reducer = reducer
) {
    override val uiState: StateFlow<NoteDetailContract.State> = uiAction
        .reduceToState(
            initialState = NoteDetailContract.State(),
            processor = ::processAction,
            reducer = ::reduceMutation,
            scope = viewModelScope
        )
}