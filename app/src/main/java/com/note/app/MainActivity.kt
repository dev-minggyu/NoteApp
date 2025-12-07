package com.note.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.note.app.navigation.NoteApp
import com.note.domain.model.AppThemeOption
import com.note.domain.repository.SettingsRepository
import com.note.feature.common.ui.theme.AppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val settingsRepository: SettingsRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeOption by settingsRepository.appTheme.collectAsState(initial = AppThemeOption.SYSTEM)
            val darkTheme = when (themeOption) {
                AppThemeOption.SYSTEM -> isSystemInDarkTheme()
                AppThemeOption.LIGHT -> false
                AppThemeOption.DARK -> true
            }

            AppTheme(darkTheme = darkTheme) {
                NoteApp()
            }
        }
    }
}