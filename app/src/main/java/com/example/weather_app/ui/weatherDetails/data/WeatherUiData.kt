package com.example.weather_app.ui.weatherDetails.data

import WeatherResponse

data class WeatherUiData(
    val city: String = "",
    val country: String = "",
    val tempC: String = "",
    val tempF: String = ""
)

fun WeatherResponse.toWeatherUiData() = WeatherUiData(
    city = this.location?.name.orEmpty(),
    country = this.location?.country.orEmpty(),
    tempC = this.current?.tempC.orEmpty(),
    tempF = this.current?.tempF.toString()
)