package com.note.app.ui.main

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    private val processor: MainProcessor,
    private val reducer: MainReducer
) : BaseViewModel<MainContract.State, MainContract.Action, MainContract.Event, MainContract.Mutation>(
    processor = processor,
    reducer = reducer
) {
    override val uiState: StateFlow<MainContract.State> = uiAction
        .reduceToState(
            initialState = MainContract.State(),
            streamIntents = setOf(
                MainContract.Action.Stream.ObserveNotes
            ),
            processor = ::processAction,
            reducer = ::reduceMutation,
            scope = viewModelScope
        )
}