package com.note.app.ui.main

import com.note.app.base.BaseReducer

class MainReducer : BaseReducer<MainContract.Mutation, MainContract.State, MainContract.Event>() {
    override fun reduce(
        currentState: MainContract.State,
        mutation: MainContract.Mutation
    ): ReduceResult<MainContract.State, MainContract.Event> {
        return when (mutation) {
            is MainContract.Mutation.ShowLoader -> stateWithEffects(
                newState = currentState.copy(isLoading = true, error = null)
            )

            is MainContract.Mutation.ShowContent -> stateWithEffects(
                newState = currentState.copy(
                    notes = mutation.notes,
                    isLoading = false,
                    error = null
                )
            )

            is MainContract.Mutation.ShowError -> stateWithEffects(
                newState = currentState.copy(isLoading = false, error = mutation.message),
                effectList = listOf(MainContract.Event.ShowToast(mutation.message))
            )

            is MainContract.Mutation.ToggleView -> stateWithEffects(
                newState = currentState.copy(isGridView = !currentState.isGridView)
            )

            is MainContract.Mutation.NoteDeleted -> stateWithEffects(
                newState = currentState,
                effectList = listOf(MainContract.Event.ShowToast("메모 삭제 완료"))
            )

            is MainContract.Mutation.NavigateToDetailMutation -> stateWithEffects(
                newState = currentState,
                effectList = listOf(MainContract.Event.NavigateToDetail(mutation.noteId))
            )
        }
    }
}
