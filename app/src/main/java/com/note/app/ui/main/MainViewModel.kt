package com.note.app.ui.main

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription

class MainViewModel(
    private val processor: MainProcessor,
    private val reducer: MainReducer
) : BaseViewModel<MainContract.Action, MainContract.Mutation, MainContract.State, MainContract.Event>(
    processor = processor,
    reducer = reducer
) {
    override val uiState: StateFlow<MainContract.State> = uiEvent
        .onSubscription { sendEvent(MainContract.Action.LoadNotes) }
        .reduceToState(
            processor = ::processEvent,
            reducer = ::reduceMutation,
            initialState = MainContract.State(),
            scope = viewModelScope
        )
}