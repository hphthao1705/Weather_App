package com.example.weather_app.ui.weatherDetails

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_app.ui.home.data.CountryUiData

@Composable
internal fun WeatherDetailsScreen(country: String) {
    val viewModel = hiltViewModel<WeatherViewModel>()
    viewModel.getWeather(country)
}