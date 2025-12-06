package com.note.feature.common.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class Colors(
    val primary: Color,
    val background: Color,
    val surface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val iconPrimary: Color,
    val iconSecondary: Color,
    val border: Color,
    val error: Color,
    val success: Color,
    val primaryContainer: Color
)

internal val LightColors = Colors(
    primary = Color(0xFF70C4A3),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    textPrimary = Color(0xFF2C2C2C),
    textSecondary = Color(0xFF757575),
    textTertiary = Color(0xFF9E9E9E),
    textDisabled = Color(0xFFBDBDBD),
    iconPrimary = Color(0xFF2C2C2C),
    iconSecondary = Color(0xFF9E9E9E),
    border = Color(0xFFE0E0E0),
    error = Color(0xFFFF5252),
    success = Color(0xFF4CAF50),
    primaryContainer = Color(0xFFE8F5F0)
)

internal val DarkColors = Colors(
    primary = Color(0xFF70C4A3),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    textPrimary = Color(0xFFE0E0E0),
    textSecondary = Color(0xFFB0B0B0),
    textTertiary = Color(0xFF757575),
    textDisabled = Color(0xFF555555),
    iconPrimary = Color(0xFFE0E0E0),
    iconSecondary = Color(0xFF757575),
    border = Color(0xFF333333),
    error = Color(0xFFFF5252),
    success = Color(0xFF4CAF50),
    primaryContainer = Color(0xFF3A4D45)
)

internal val LocalAppColors = compositionLocalOf { LightColors }