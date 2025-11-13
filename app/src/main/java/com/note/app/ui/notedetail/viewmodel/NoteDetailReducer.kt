package com.note.app.ui.notedetail.viewmodel

import com.note.app.base.BaseReducer
import com.note.app.ui.notedetail.NoteDetailContract

class NoteDetailReducer :
    BaseReducer<NoteDetailContract.Mutation, NoteDetailContract.State, NoteDetailContract.Event>() {

    override fun reduce(
        currentState: NoteDetailContract.State,
        mutation: NoteDetailContract.Mutation
    ): ReduceResult<NoteDetailContract.State, NoteDetailContract.Event> {
        return when (mutation) {
            is NoteDetailContract.Mutation.ShowProgress -> stateWithEvents(
                newState = currentState.copy(isLoading = true, error = null)
            )

            is NoteDetailContract.Mutation.NoteLoaded -> stateWithEvents(
                newState = currentState.copy(
                    note = mutation.note,
                    title = mutation.note.title,
                    content = mutation.note.content,
                    isLoading = false
                )
            )

            is NoteDetailContract.Mutation.TitleUpdated -> stateWithEvents(
                newState = currentState.copy(title = mutation.title)
            )

            is NoteDetailContract.Mutation.ContentUpdated -> stateWithEvents(
                newState = currentState.copy(content = mutation.content)
            )

            is NoteDetailContract.Mutation.SavingNote -> stateWithEvents(
                newState = currentState.copy(isSaving = true)
            )

            is NoteDetailContract.Mutation.NoteSavedSuccess -> stateWithEvents(
                newState = currentState.copy(isSaving = false),
                eventList = listOf(NoteDetailContract.Event.NoteSaved)
            )

            is NoteDetailContract.Mutation.ShowError -> stateWithEvents(
                newState = currentState.copy(
                    isLoading = false,
                    isSaving = false,
                    error = mutation.error
                ),
                eventList = listOf(NoteDetailContract.Event.ShowError(mutation.error))
            )

            is NoteDetailContract.Mutation.NavigateBackMutation -> stateWithEvents(
                newState = currentState,
                eventList = listOf(NoteDetailContract.Event.NavigateBack)
            )
        }
    }
}