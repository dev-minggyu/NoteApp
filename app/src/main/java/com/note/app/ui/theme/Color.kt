package com.note.app.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val primary: Color,
    val contentBackground: Color,
    val titleText: Color,
    val subTitleText: Color,
    val cardBackground: Color,
    val toggleTint: Color,
    val fabTint: Color,
    val topBarBackground: Color,
    val emptyText: Color,
)

val LightColors = Colors(
    primary = Color(0xFF70C4A3),
    contentBackground = Color(0xFFF5F5F5),
    titleText = Color(0xFF2C2C2C),
    subTitleText = Color(0xFF757575),
    cardBackground = Color.White,
    toggleTint = Color(0xFF9E9E9E),
    fabTint = Color.White,
    topBarBackground = Color(0xFFF5F5F5),
    emptyText = Color(0xFF9E9E9E),
)

val DarkColors = Colors(
    primary = Color(0xFF70C4A3),
    contentBackground = Color(0xFF121212),
    titleText = Color(0xFFE0E0E0),
    subTitleText = Color(0xFFB0B0B0),
    cardBackground = Color(0xFF1E1E1E),
    toggleTint = Color(0xFF757575),
    fabTint = Color(0xFF2C2C2C),
    topBarBackground = Color(0xFF1E1E1E),
    emptyText = Color(0xFF757575),
)