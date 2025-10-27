package com.note.app.ui.splash

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription

class InitViewModel(
    processor: InitProcessor,
    reducer: InitReducer
) : BaseViewModel<InitContract.Event, InitContract.Mutation, InitContract.State, InitContract.Effect>(
    processor = processor,
    reducer = reducer
) {
    override val uiState: StateFlow<InitContract.State> = uiEvent
        .onSubscription { sendEvent(InitContract.Event.Initialize) }
        .reduceToState(
            processor = ::processEvent,
            reducer = ::reduceMutation,
            initialState = InitContract.State(),
            scope = viewModelScope
        )
}