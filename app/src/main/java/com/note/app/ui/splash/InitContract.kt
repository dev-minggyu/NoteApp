package com.note.app.ui.splash

import com.note.app.base.contract.UiEffect
import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState

class InitContract {
    sealed interface Event : UiEvent {
        data object Initialize : Event
    }

    data class State(
        val isInitialized: Boolean = false,
        val error: String? = null
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToMain : Effect
        data class ShowError(val message: String) : Effect
    }

    sealed interface Mutation : UiMutation {
        data object InitComplete : Mutation
    }
}