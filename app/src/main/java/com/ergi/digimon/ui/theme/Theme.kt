package com.ergi.digimon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = DigiOrange,
    onPrimary = Color.White,
    secondary = DigiBlue,
    onSecondary = Color.White,
    tertiary = DigiTeal,
    background = LightBg,
    surface = LightSurface,
    surfaceVariant = Color(0xFFFFF3E0),
    onBackground = Color(0xFF1B1B1F),
    onSurface = Color(0xFF1B1B1F)
)

private val DarkColors = darkColorScheme(
    primary = DigiOrangeDark,
    onPrimary = Color(0xFF3E2000),
    secondary = DigiBlueLight,
    onSecondary = Color(0xFF002A5C),
    tertiary = DigiTealLight,
    background = DarkBg,
    surface = DarkSurface,
    surfaceVariant = Color(0xFF24304D),
    onBackground = Color(0xFFE4E6EF),
    onSurface = Color(0xFFE4E6EF)
)

@Composable
fun DigimonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = DigiTypography,
        content = content
    )
}