package com.note.app.ui.main

import com.note.app.base.BaseReducer

class MainReducer : BaseReducer<MainContract.Mutation, MainContract.State, MainContract.Event>() {
    override fun reduce(
        currentState: MainContract.State,
        mutation: MainContract.Mutation
    ): ReduceResult<MainContract.State, MainContract.Event> {
        return when (mutation) {
            is MainContract.Mutation.ShowProgress -> stateWithEvents(
                newState = currentState.copy(isLoading = true, error = null)
            )

            is MainContract.Mutation.NoteLoaded -> stateWithEvents(
                newState = currentState.copy(
                    notes = mutation.notes,
                    isLoading = false,
                    error = null
                )
            )

            is MainContract.Mutation.ShowError -> stateWithEvents(
                newState = currentState.copy(isLoading = false, error = mutation.error),
                eventList = listOf(MainContract.Event.ShowError(mutation.error))
            )

            is MainContract.Mutation.ToggleView -> stateWithEvents(
                newState = currentState.copy(isGrid = !currentState.isGrid)
            )

            is MainContract.Mutation.NoteDeleted -> stateWithEvents(
                newState = currentState
            )

            is MainContract.Mutation.NavigateToDetailMutation -> stateWithEvents(
                newState = currentState,
                eventList = listOf(MainContract.Event.NavigateToDetail(mutation.noteId))
            )
        }
    }
}
