package com.note.feature.notedetail.viewmodel

import com.note.feature.common.ui.base.BaseReducer
import com.note.feature.notedetail.NoteDetailContract

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
                    alarmTime = mutation.note.alarmTime.takeIf { it > 0 },
                    alarmMessage = mutation.note.alarmMessage,
                    isAlarmEnabled = mutation.note.isAlarmEnabled,
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

            is NoteDetailContract.Mutation.SetAlarm -> stateWithEvents(
                newState = currentState.copy(alarmTime = mutation.time, alarmMessage = mutation.message)
            )

            is NoteDetailContract.Mutation.ToggleAlarm -> stateWithEvents(
                newState = currentState.copy(isAlarmEnabled = mutation.isEnabled)
            )
        }
    }
}