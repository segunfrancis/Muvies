package com.segunfrancis.muvies.common.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

@Composable
fun MuviesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColors,
        typography = MuviesTypography,
        shapes = MuviesShape,
        content = content
    )
}

private val DarkColors = darkColors(
    primary = Red900,
    primaryVariant = Red900,
    secondary = Yellow,
    onSecondary = White,
    error = Red600,
    surface = Red1000,
    onSurface = Grey400
)
