package com.example.weather_app.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object WeatherTheme {
    val colors: WeatherColorScheme
        @Composable
        get() = LocalWeatherColorScheme.current
}

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) weatherDarkColors else weatherLightColors

    CompositionLocalProvider(LocalWeatherColorScheme provides colors) {
        content()
    }
}

data class WeatherColorScheme(
    val textPrimary: Color = Color.Unspecified,
    val brandColor: Color = Color.Unspecified,
    val onBrandColor: Color = Color.Unspecified,
    val textSecondary: Color = Color.Unspecified,
    val backgroundError: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
)

private val weatherLightColors = WeatherColorScheme(
    textPrimary = Color(0xFF141413),
    textSecondary = Color(0xFF6B6A71),
    brandColor = Color(0xFF6151C3),
    onBrandColor = Color(0xFFFFFFFFF),
    background = Color(0xFF070707),
    backgroundError = Color(0xFFF8F8F8)
)

private val weatherDarkColors = WeatherColorScheme(
    textPrimary = Color(0xFFFFFFFFF),
    textSecondary = Color(0xFF6B6A71),
    brandColor = Color(0xFF6151C3),
    onBrandColor = Color(0xFF141413),
    background = Color(0xFFF8F8F8),
    backgroundError = Color(0xFFF8F8F8)
)

val LocalWeatherColorScheme = staticCompositionLocalOf {
    WeatherColorScheme()
}