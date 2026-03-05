package com.example.weather_app.ui.home.data

import android.os.Parcelable
import com.example.weather_app.data.CountryResponse
import kotlinx.android.parcel.Parcelize

// if not use Parcelize, Android can't store inside Bundle => crash
@Parcelize
data class CountryUiData (
    val name: String?,
    val capital: String?,
    val region: String?,
    val flags: String?
): Parcelable

fun CountryResponse.toCountryUiData() = CountryUiData(
    name = this.name,
    capital = this.capital,
    region = this.region,
    flags = this.flags?.png
)

fun List<CountryResponse>.toCountryUiDataList(): List<CountryUiData> {
    return this.map { response -> response.toCountryUiData() }
}