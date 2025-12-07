package com.note.feature.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.note.domain.model.AppThemeOption
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.setting.R

@Composable
fun ThemeSettingSection(
    currentTheme: AppThemeOption,
    onThemeSelected: (AppThemeOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.setting_theme_title),
            style = AppTheme.typo.titleLarge,
            color = AppTheme.colors.textPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.selectableGroup()) {
            AppThemeOption.entries.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (option == currentTheme),
                            onClick = { onThemeSelected(option) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == currentTheme),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = AppTheme.colors.primary,
                            unselectedColor = AppTheme.colors.iconSecondary
                        )
                    )
                    Text(
                        text = when (option) {
                            AppThemeOption.SYSTEM -> stringResource(R.string.setting_theme_system)
                            AppThemeOption.LIGHT -> stringResource(R.string.setting_theme_light)
                            AppThemeOption.DARK -> stringResource(R.string.setting_theme_dark)
                        },
                        style = AppTheme.typo.bodySmall,
                        color = AppTheme.colors.textSecondary,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}
