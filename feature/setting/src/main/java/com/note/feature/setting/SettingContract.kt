package com.note.feature.setting

import com.note.domain.model.AppThemeOption
import com.note.feature.common.ui.base.contract.UiAction
import com.note.feature.common.ui.base.contract.UiEvent
import com.note.feature.common.ui.base.contract.UiMutation
import com.note.feature.common.ui.base.contract.UiState

class SettingContract {
    sealed interface Action : UiAction {
        data class SetTheme(val option: AppThemeOption) : Action
        data object LoadTheme : Action
    }

    data class State(
        val appTheme: AppThemeOption = AppThemeOption.SYSTEM
    ) : UiState

    sealed interface Event : UiEvent

    sealed interface Mutation : UiMutation {
        data class UpdateTheme(val option: AppThemeOption) : Mutation
    }
}
