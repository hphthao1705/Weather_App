package com.example.weather_app.data

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("capital")
    val capital: String? = null,
    @SerializedName("subregion")
    val subRegion: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("population")
    val population: Long? = null,
    @SerializedName("latlng")
    val lat: List<Double>? = null,
    @SerializedName("demonym")
    val demonym: String? = null,
    @SerializedName("timezones")
    val timezones: List<String>? = null,
    @SerializedName("nativeName")
    val nativeName: String? = null,
    @SerializedName("numericCode")
    val numericCode: String? = null,
    @SerializedName("flags")
    val flags: Flag? = null,
    @SerializedName("currencies")
    val currencies: List<Currency>? = null,
    @SerializedName("languages")
    val languages: List<Language>? = null,
    @SerializedName("flag")
    val flag: String? = null,
)

data class Flag(
    @SerializedName("svg")
    val svg: String? = null,
    @SerializedName("png")
    val png: String? = null
)

data class Currency(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("symbol")
    val symbol: String? = null
)

data class Language(
    @SerializedName("iso639_1")
    val iso6391: String? = null,
    @SerializedName("iso639_2")
    val iso6392: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("nativeName")
    val nativeName: String? = null
)