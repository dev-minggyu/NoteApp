package com.note.app.ui.main

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription

class MainViewModel(
    private val processor: MainProcessor,
    private val reducer: MainReducer
) : BaseViewModel<MainContract.Event, MainContract.Mutation, MainContract.State, MainContract.Effect>(
    processor = processor,
    reducer = reducer
) {
    override val uiState: StateFlow<MainContract.State> = uiEvent
        .onSubscription { sendEvent(MainContract.Event.LoadData) }
        .reduceToState(
            processor = ::processEvent,
            reducer = ::reduceMutation,
            initialState = MainContract.State(),
            scope = viewModelScope
        )
}