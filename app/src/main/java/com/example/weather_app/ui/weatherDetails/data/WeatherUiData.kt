package com.example.weather_app.ui.weatherDetails.data

import WeatherResponse

data class WeatherUiData(
    val city: String = "",
    val country: String = "",
    val tempC: String = "",
    val tempF: String = "",
    val wind: String = "",
    val pressure: String = "",
    val cards: List<Card>
)

data class Card(
    val image: String = "",
    val location: String = "",
    val temperature: String = "",
    val condition: String = ""
)

fun WeatherResponse.toWeatherUiData(): WeatherUiData {
    val card1 = Card(
        image = "https:${this.current?.condition?.icon}",
        location = this.location?.name.orEmpty(),
        temperature = "${this.current?.tempC}°C",
        condition = this.current?.condition?.text.orEmpty()
    )
    return WeatherUiData(
        city = this.location?.name.orEmpty(),
        country = this.location?.country.orEmpty(),
        tempC = "${this.current?.tempC}°C",
        tempF = "${this.current?.tempF.toString()}°F",
        wind = "${this.current?.windMph.toString()} mph",
        pressure = "${this.current?.pressureMb.toString()} mb",
        cards = listOf(card1, card1, card1, card1, card1)
    )
}
