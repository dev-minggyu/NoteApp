package com.note.app.ui.main

import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiAction
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState

class MainContract {
    sealed interface Action : UiAction {
        data object LoadData : Action
        data class ClickItem(val id: String) : Action
    }

    data class State(
        val isLoading: Boolean = false,
        val data: List<String> = emptyList(),
        val error: String? = null
    ) : UiState

    sealed interface Event : UiEvent {
        data class ShowToast(val message: String) : Event
        data class NavigateToDetail(val id: String) : Event
    }

    sealed interface Mutation : UiMutation {
        data object ShowLoader : Mutation
        data class ShowContent(val list: List<String>) : Mutation
        data class ShowError(val msg: String) : Mutation
        data class ItemClicked(val id: String) : Mutation
    }
}