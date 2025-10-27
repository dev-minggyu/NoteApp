package com.note.app.ui.main

import com.note.app.base.BaseReducer

class MainReducer : BaseReducer<MainContract.Mutation, MainContract.State, MainContract.Effect>() {
    override fun reduce(
        currentState: MainContract.State, mutation: MainContract.Mutation
    ): ReduceResult<MainContract.State, MainContract.Effect> {
        return when (mutation) {
            is MainContract.Mutation.ItemClicked -> stateWithEffects(
                newState = currentState,
                effectList = listOf(MainContract.Effect.NavigateToDetail(mutation.id))
            )

            is MainContract.Mutation.ShowContent -> stateWithEffects(
                newState = currentState.copy(isLoading = false, data = mutation.list),
                effectList = listOf(MainContract.Effect.ShowToast("데이터 로드 완료"))
            )

            MainContract.Mutation.ShowLoader -> stateWithEffects(
                newState = currentState.copy(isLoading = true)
            )

            is MainContract.Mutation.ShowError -> stateWithEffects(
                newState = currentState,
                effectList = listOf(MainContract.Effect.ShowToast(mutation.msg))
            )
        }
    }
}