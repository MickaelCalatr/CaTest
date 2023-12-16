package com.antartic.sudio.ui_ds.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
private fun getDarkColors() = darkColorScheme(
    primary = primary,
    onPrimary = white,
    secondary = secondary,
    onSecondary = black,
    tertiary = secondary,
    onTertiary = black,

    surface = neutral1000,
    onSurface = white,
    surfaceVariant = neutral800,
    onSurfaceVariant = neutral100,
    inverseSurface = neutral100,
    inverseOnSurface = black,

    primaryContainer = neutral900,
    secondaryContainer = neutral800,
    tertiaryContainer = neutral300,
    onPrimaryContainer = neutral200,
    onSecondaryContainer = neutral300,
    onTertiaryContainer = black,

    background = black,
    onBackground = neutral100,
)

@Composable
private fun getLightColors() = lightColorScheme(
    primary = primary,
    onPrimary = white,
    secondary = secondary,
    onSecondary = white,
    tertiary = secondary,
    onTertiary = white,

    surface = neutral100,
    onSurface = neutral1000,
    inverseSurface = neutral1000,
    inverseOnSurface = white,

    primaryContainer = white,
    secondaryContainer = neutral200,
    tertiaryContainer = neutral300,
    onPrimaryContainer = neutral1000,
    onSecondaryContainer = neutral800,
    onTertiaryContainer = neutral300,

    background = white,
    onBackground = black
)

@Composable
fun CATheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = when {
        useDarkTheme -> getDarkColors()
        else -> getLightColors()
    }
    MaterialTheme(
        colorScheme = colors,
        shapes = shapes,
        content = content
    )
}
