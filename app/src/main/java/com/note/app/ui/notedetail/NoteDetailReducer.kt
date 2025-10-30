package com.note.app.ui.notedetail

import com.note.app.base.BaseReducer

class NoteDetailReducer :
    BaseReducer<NoteDetailContract.Mutation, NoteDetailContract.State, NoteDetailContract.Event>() {

    override fun reduce(
        currentState: NoteDetailContract.State,
        mutation: NoteDetailContract.Mutation
    ): ReduceResult<NoteDetailContract.State, NoteDetailContract.Event> {
        return when (mutation) {
            is NoteDetailContract.Mutation.ShowLoader -> stateWithEffects(
                newState = currentState.copy(isLoading = true, error = null)
            )

            is NoteDetailContract.Mutation.NoteLoaded -> stateWithEffects(
                newState = currentState.copy(
                    note = mutation.note,
                    title = mutation.note.title,
                    content = mutation.note.content,
                    isLoading = false
                )
            )

            is NoteDetailContract.Mutation.TitleUpdated -> stateWithEffects(
                newState = currentState.copy(title = mutation.title)
            )

            is NoteDetailContract.Mutation.ContentUpdated -> stateWithEffects(
                newState = currentState.copy(content = mutation.content)
            )

            is NoteDetailContract.Mutation.SavingNote -> stateWithEffects(
                newState = currentState.copy(isSaving = true)
            )

            is NoteDetailContract.Mutation.NoteSavedSuccess -> stateWithEffects(
                newState = currentState.copy(isSaving = false),
                effectList = listOf(NoteDetailContract.Event.NoteSaved)
            )

            is NoteDetailContract.Mutation.ShowError -> stateWithEffects(
                newState = currentState.copy(
                    isLoading = false,
                    isSaving = false,
                    error = mutation.error
                ),
                effectList = listOf(NoteDetailContract.Event.ShowError(mutation.error))
            )

            is NoteDetailContract.Mutation.NavigateBackMutation -> stateWithEffects(
                newState = currentState,
                effectList = listOf(NoteDetailContract.Event.NavigateBack)
            )
        }
    }
}