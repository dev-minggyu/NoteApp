package com.note.app.ui.main

import com.note.app.base.contract.UiEffect
import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState

class MainContract {
    sealed interface Event : UiEvent {
        data object LoadData : Event
        data class ClickItem(val id: String) : Event
    }

    data class State(
        val isLoading: Boolean = false,
        val data: List<String> = emptyList(),
        val error: String? = null
    ) : UiState

    sealed interface Effect : UiEffect {
        data class ShowToast(val message: String) : Effect
        data class NavigateToDetail(val id: String) : Effect
    }

    sealed interface Mutation : UiMutation {
        data object ShowLoader : Mutation
        data class ShowContent(val list: List<String>) : Mutation
        data class ShowError(val msg: String) : Mutation
        data class ItemClicked(val id: String) : Mutation
    }
}