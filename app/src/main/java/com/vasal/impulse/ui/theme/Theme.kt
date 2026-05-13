package com.vasal.impulse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = SeedGreen,
    secondaryContainer = WarmSurface,
    background = WarmBackground,
    surface = WarmSurface,
    onBackground = Ink,
    onSurface = Ink
)

private val DarkColorScheme = darkColorScheme()

@Composable
fun ImpulseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}

