package com.note.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

val LocalAppColors = compositionLocalOf { LightColors }

object AppTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkColors else LightColors
    CompositionLocalProvider(LocalAppColors provides appColors) {
        content()
    }
}