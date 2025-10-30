package com.note.app.ui.splash

import androidx.lifecycle.viewModelScope
import com.note.app.base.BaseViewModel
import com.note.app.utils.extension.reduceToState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription

class InitViewModel(
    processor: InitProcessor,
    reducer: InitReducer
) : BaseViewModel<InitContract.Action, InitContract.Mutation, InitContract.State, InitContract.Event>(
    processor = processor,
    reducer = reducer
) {
    override val uiState: StateFlow<InitContract.State> = uiAction
        .onSubscription { sendAction(InitContract.Action.Initialize) }
        .reduceToState(
            processor = ::processAction,
            reducer = ::reduceMutation,
            initialState = InitContract.State(),
            scope = viewModelScope
        )
}