package com.note.app.ui.splash

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription

class InitViewModel(
    processor: InitProcessor,
    reducer: InitReducer
) : BaseViewModel<InitContract.State, InitContract.Action, InitContract.Event, InitContract.Mutation>(
    processor = processor,
    reducer = reducer
) {
    override val uiState: StateFlow<InitContract.State> = uiAction
        .onSubscription { sendAction(InitContract.Action.Initialize) }
        .reduceToState(
            initialState = InitContract.State(),
            processor = ::processAction,
            reducer = ::reduceMutation,
            scope = viewModelScope
        )
}