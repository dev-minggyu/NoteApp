package com.note.feature.setting.viewmodel

import com.note.feature.common.ui.base.BaseReducer
import com.note.feature.setting.SettingContract

class SettingReducer : BaseReducer<SettingContract.Mutation, SettingContract.State, SettingContract.Event>() {
    override fun reduce(
        state: SettingContract.State,
        mutation: SettingContract.Mutation
    ): ReduceResult<SettingContract.State, SettingContract.Event> {
        return when (mutation) {
            is SettingContract.Mutation.UpdateTheme -> {
                stateWithEvents(state.copy(appTheme = mutation.option))
            }
        }
    }
}
