package com.note.app.base

import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState

abstract class BaseReducer<Mutation : UiMutation, State : UiState, Event : UiEvent> {
    data class ReduceResult<State : UiState, Event : UiEvent>(val newState: State, val events: List<Event>)

    abstract fun reduce(currentState: State, mutation: Mutation): ReduceResult<State, Event>

    protected fun stateWithEvents(
        newState: State,
        eventList: List<Event> = emptyList()
    ): ReduceResult<State, Event> = ReduceResult(newState, eventList)
}