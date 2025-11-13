package com.note.app.ui.main.viewmodel

import com.note.app.base.BaseReducer
import com.note.app.ui.main.MainContract
import com.note.app.ui.main.MainContract.Event.NavigateToDetail
import com.note.app.ui.main.MainContract.Event.ShowError

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
                newState = currentState.copy(notes = mutation.notes, isLoading = false, error = null)
            )

            is MainContract.Mutation.ShowError -> stateWithEvents(
                newState = currentState.copy(isLoading = false, error = mutation.error),
                eventList = listOf(ShowError(mutation.error))
            )

            is MainContract.Mutation.ToggleView -> stateWithEvents(
                newState = currentState.copy(isGrid = !currentState.isGrid)
            )

            is MainContract.Mutation.NoteDeleted -> stateWithEvents(
                newState = currentState
            )

            is MainContract.Mutation.NavigateToDetailMutation -> stateWithEvents(
                newState = currentState,
                eventList = listOf(NavigateToDetail(mutation.noteId))
            )

            is MainContract.Mutation.UpdateSearchQuery -> stateWithEvents(
                newState = currentState.copy(searchQuery = mutation.searchQuery)
            )

            is MainContract.Mutation.SearchResult -> stateWithEvents(
                newState = currentState.copy(notes = mutation.notes, isLoading = false, error = null)
            )
        }
    }
}
