package com.note.domain.repository

import com.note.domain.model.AppThemeOption
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val appTheme: Flow<AppThemeOption>
    suspend fun setAppTheme(option: AppThemeOption)
}
