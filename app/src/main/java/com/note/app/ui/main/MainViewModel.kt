package com.note.app.ui.main

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription

class MainViewModel(
    private val processor: MainProcessor,
    private val reducer: MainReducer
) : BaseViewModel<MainContract.State, MainContract.Action, MainContract.Event, MainContract.Mutation>(
    processor = processor,
    reducer = reducer
) {
        .onSubscription { sendEvent(MainContract.Action.LoadNotes) }
    override val uiState: StateFlow<MainContract.State> = uiAction
        .reduceToState(
            processor = ::processAction,
            reducer = ::reduceMutation,
            initialState = MainContract.State(),
            scope = viewModelScope
        )
}