package com.note.feature.setting.viewmodel

import com.note.domain.repository.SettingsRepository
import com.note.feature.common.ui.base.BaseProcessor
import com.note.feature.setting.SettingContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class SettingProcessor(
    private val settingsRepository: SettingsRepository
) : BaseProcessor<SettingContract.Action, SettingContract.Mutation>() {

    override fun process(action: SettingContract.Action): Flow<SettingContract.Mutation> {
        return when (action) {
            is SettingContract.Action.SetTheme -> setTheme(action)
            is SettingContract.Action.Stream.LoadTheme -> loadTheme()
        }
    }

    private fun setTheme(action: SettingContract.Action.SetTheme): Flow<SettingContract.Mutation> {
        return flow {
            settingsRepository.setAppTheme(action.option)
            emit(SettingContract.Mutation.UpdateTheme(action.option))
        }
    }

    private fun loadTheme(): Flow<SettingContract.Mutation> {
        return settingsRepository.appTheme
            .map { SettingContract.Mutation.UpdateTheme(it) }
    }
}
