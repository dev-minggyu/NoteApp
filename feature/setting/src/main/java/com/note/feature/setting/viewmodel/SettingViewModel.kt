package com.note.feature.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.note.feature.common.extension.reduceToState
import com.note.feature.common.ui.base.BaseViewModel
import com.note.feature.setting.SettingContract
import kotlinx.coroutines.flow.StateFlow

class SettingViewModel(
    private val processor: SettingProcessor,
    private val reducer: SettingReducer
) : BaseViewModel<SettingContract.State, SettingContract.Action, SettingContract.Event, SettingContract.Mutation>(
    processor = processor,
    reducer = reducer
) {

    override val uiState: StateFlow<SettingContract.State> = uiAction
        .reduceToState(
            initialState = SettingContract.State(),
            streamIntents = setOf(
                SettingContract.Action.Stream.LoadTheme
            ),
            processor = ::processAction,
            reducer = ::reduceMutation,
            scope = viewModelScope
        )
}
