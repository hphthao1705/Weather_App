package com.example.weather_app.ui.home.data

data class HomeWeatherUiData(
    val state: String = "",
    val country: String = "",
    val weatherCondition: String = "",
    val temperatureCelsius: Int = 0,
    val weatherImage: String = "",
)