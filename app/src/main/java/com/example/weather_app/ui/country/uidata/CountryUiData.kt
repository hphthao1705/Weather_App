package com.example.weather_app.ui.country.uidata

import com.example.weather_app.data.CountryResponse

data class CountryUiData (
    val name: String?,
    val capital: String?,
    val region: String?,
    val flags: String?
)

fun CountryResponse.toCountryUiData() = CountryUiData(
    name = this.name,
    capital = this.capital,
    region = this.region,
    flags = this.flags?.png
)

fun List<CountryResponse>.toCountryUiDataList(): List<CountryUiData> {
    return this.map { response -> response.toCountryUiData() }
}