package com.note.app.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.note.app.base.contract.UiEffect
import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, Mutation : UiMutation, State : UiState, Effect : UiEffect>(
    private val processor: BaseProcessor<Event, Mutation>,
    private val reducer: BaseReducer<Mutation, State, Effect>,
) : ViewModel() {

    abstract val uiState: StateFlow<State>

    protected val uiEvent = MutableSharedFlow<Event>()

    private val _uiEffect = Channel<Effect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    protected fun processEvent(event: Event): Flow<Mutation> {
        return processor.process(event)
    }

    protected fun reduceMutation(state: State, mutation: Mutation): State {
        val (newState, effects) = reducer.reduce(state, mutation)
        effects.forEach { sendEffect(it) }
        return newState
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            this@BaseViewModel.uiEvent.emit(event)
        }
    }
}