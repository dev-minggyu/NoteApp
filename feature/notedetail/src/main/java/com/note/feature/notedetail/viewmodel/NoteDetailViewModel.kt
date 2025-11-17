package com.note.feature.notedetail.viewmodel

import androidx.lifecycle.viewModelScope
import com.note.feature.common.extension.reduceToState
import com.note.feature.common.ui.base.BaseViewModel
import com.note.feature.notedetail.NoteDetailContract
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