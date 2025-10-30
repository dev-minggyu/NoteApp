package com.note.app.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.note.app.base.contract.UiAction
import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Action : UiAction, Mutation : UiMutation, State : UiState, Event : UiEvent>(
    private val processor: BaseProcessor<Action, Mutation>,
    private val reducer: BaseReducer<Mutation, State, Event>,
) : ViewModel() {

    abstract val uiState: StateFlow<State>

    protected val uiAction = MutableSharedFlow<Action>()

    private val _uiEvent = Channel<Event>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    protected fun processAction(action: Action): Flow<Mutation> {
        return processor.process(action)
    }

    protected fun reduceMutation(state: State, mutation: Mutation): State {
        val (newState, events) = reducer.reduce(state, mutation)
        events.forEach { sendEvent(it) }
        return newState
    }

    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun sendAction(action: Action) {
        viewModelScope.launch {
            this@BaseViewModel.uiAction.emit(action)
        }
    }
}