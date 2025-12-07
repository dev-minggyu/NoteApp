package com.note.feature.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.note.core.navigation.AppNavigator
import com.note.core.navigation.Screen
import com.note.feature.setting.SettingScreen

fun NavGraphBuilder.settingNavGraph(
    navigator: AppNavigator
) {
    composable<Screen.Setting> {
        SettingScreen()
    }
}
