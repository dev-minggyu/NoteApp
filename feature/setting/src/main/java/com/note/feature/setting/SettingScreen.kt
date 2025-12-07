package com.note.feature.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.note.domain.model.AppThemeOption
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.setting.component.ThemeSettingSection
import com.note.feature.setting.viewmodel.SettingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendAction(SettingContract.Action.Stream.LoadTheme)
    }

    Scaffold(
        containerColor = AppTheme.colors.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.setting_title),
                style = AppTheme.typo.headlineSmall,
                color = AppTheme.colors.textPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            ThemeSettingSection(
                currentTheme = uiState.appTheme,
                onThemeSelected = { viewModel.sendAction(SettingContract.Action.SetTheme(option = it)) }
            )
        }
    }
}
