package com.example.weather_app.ui

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * [primary]: Main brand color used for prominent components (e.g., buttons, active elements).
 * [onPrimary]: Text and icons on top of the [primary] color.
 * [secondary]: Secondary brand color used for less prominent components (e.g., secondary elements).
 * [background]: Background color for the entire app.
 * [onBackground]: Text and icons on top of the [background] color.
 * [onSurface]: Text and icons on top of the [surface] color.]
 */
private val lightColors = lightColorScheme(
    primary = Color(0xFFF8F8F8),
    background = Color(0xFFF8F8F8), //background color
    onBackground = Color(0xFF070707), //text color
    onSurface = Color(0xFF070707),
)

private val darkColors = darkColorScheme(
    primary = Color(0xFF070707),
    background = Color(0xFF070707),
    onBackground = Color(0xFFF8F8F8),
    onSurface = Color(0xFFF8F8F8),
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Log.d("Thao Ho", "isDarkTheme: $darkTheme")
    val colors = if (darkTheme) darkColors else lightColors
    MaterialTheme (
        colorScheme = colors,
        content = content
    )
}