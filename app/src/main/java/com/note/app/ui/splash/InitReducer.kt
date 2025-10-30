package com.note.app.ui.splash

import com.note.app.base.BaseReducer

class InitReducer : BaseReducer<InitContract.Mutation, InitContract.State, InitContract.Event>() {
    override fun reduce(
        currentState: InitContract.State,
        mutation: InitContract.Mutation
    ): ReduceResult<InitContract.State, InitContract.Event> {
        return when (mutation) {
            InitContract.Mutation.InitComplete -> stateWithEvents(
                newState = currentState.copy(isInitialized = true),
                eventList = listOf(InitContract.Event.NavigateToMain)
            )
        }
    }
}