package com.note.core.repository.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.note.domain.model.AppThemeOption
import com.note.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    override val appTheme: Flow<AppThemeOption> = dataStore.data
        .map { preferences ->
            val optionName = preferences[KEY_THEME_OPTION] ?: AppThemeOption.SYSTEM.name
            AppThemeOption.valueOf(optionName)
        }

    override suspend fun setAppTheme(option: AppThemeOption) {
        dataStore.edit { preferences ->
            preferences[KEY_THEME_OPTION] = option.name
        }
    }

    companion object {
        private val KEY_THEME_OPTION = stringPreferencesKey("APP_THEME_OPTION")
    }
}
