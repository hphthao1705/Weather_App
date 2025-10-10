package com.example.weather_app.data

data class CountryResponse(
    val name: String? = null,
    val capital: String? = null,
    val subregion: String? = null,
    val region: String? = null,
    val population: Long? = null,
    val latlng: List<Double>? = null,
    val demonym: String? = null,
    val timezones: List<String>? = null,
    val nativeName: String? = null,
    val numericCode: String? = null,
    val flags: Flag? = null,
    val currencies: List<Currency>? = null,
    val languages: List<Language>? = null,
    val flag: String? = null,
)

data class Flag(
    val svg: String? = null,
    val png: String? = null
)

data class Currency(
    val code: String? = null,
    val name: String? = null,
    val symbol: String? = null
)

data class Language(
    val iso639_1: String? = null,
    val iso639_2: String? = null,
    val name: String? = null,
    val nativeName: String? = null
)