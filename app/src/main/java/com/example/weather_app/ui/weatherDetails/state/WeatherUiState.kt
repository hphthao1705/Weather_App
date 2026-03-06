package com.example.weather_app.ui.weatherDetails.state

import com.example.weather_app.ui.home.data.CountryUiData
import com.example.weather_app.ui.home.data.WeatherUiData

sealed class WeatherUiState {
    data class Success(val data: WeatherUiData) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
    object Loading : WeatherUiState()
    object StandBy : WeatherUiState()
}