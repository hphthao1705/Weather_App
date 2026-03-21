package com.example.weather_app.ui.weatherDetails.data

import WeatherResponse

data class WeatherUiData(
    val city: String = "",
    val country: String = "",
    val tempC: String = "",
    val tempF: String = "",
    val cards: List<Card>
)

data class Card(
    val image: String = "",
    val location: String = "",
    val temperature: String = "",
    val condition: String = ""
)

fun WeatherResponse.toWeatherUiData() : WeatherUiData {
    val card1 = Card(
        image = "https:${this.current?.condition?.icon}",
        location = this.location?.name.orEmpty(),
        temperature = this.current?.condition?.code.toString(),
        condition = this.current?.condition?.text.orEmpty()
    )
    return WeatherUiData(
        city = this.location?.name.orEmpty(),
        country = this.location?.country.orEmpty(),
        tempC = this.current?.tempC.orEmpty(),
        tempF = this.current?.tempF.toString(),
        cards = listOf(card1, card1, card1)
    )
}
