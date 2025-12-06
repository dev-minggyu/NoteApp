package com.note.feature.common.ui.theme


import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

private val DefaultTextStyle = TextStyle.Default.copy(
    platformStyle = PlatformTextStyle(includeFontPadding = false),
    lineHeightStyle = LineHeightStyle(alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.None),
)

data class Typo(
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle
)

internal val AppTypography = Typo(
    headlineLarge = DefaultTextStyle.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold),
    headlineMedium = DefaultTextStyle.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold),
    headlineSmall = DefaultTextStyle.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
    titleLarge = DefaultTextStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
    titleMedium = DefaultTextStyle.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
    titleSmall = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Bold),
    bodyLarge = DefaultTextStyle.copy(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    bodyMedium = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    bodySmall = DefaultTextStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Normal),
    labelLarge = DefaultTextStyle.copy(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    labelMedium = DefaultTextStyle.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium),
    labelSmall = DefaultTextStyle.copy(fontSize = 11.sp, fontWeight = FontWeight.Medium)
)