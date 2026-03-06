package com.example.weather_app.api

import WeatherResponse
import com.example.weather_app.api.ApiEndPoint.GET_ALL_COUNTRY
import com.example.weather_app.api.ApiEndPoint.GET_WEATHER
import com.example.weather_app.data.CountryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// TODO - TH: this key will be handled in the next commit pls
const val COUNTRY_API_KEY = "xEFf3OXcOXy0KsRCWzlVMQ==o18XyHGnWMgIviiF"
const val WEATHER_API_KEY = "e0f033957e784ed999e34806242406"

interface CountryApiClient {
    @GET(GET_ALL_COUNTRY)
    suspend fun getAllCountry(
        @Header("X-Api-Key") key: String = COUNTRY_API_KEY
    ): Response<List<CountryResponse>>
}

interface WeatherApiClient {
    @GET(GET_WEATHER)
    suspend fun getWeather(
        @Header("key") key: String = WEATHER_API_KEY,
        @Query("q") cityName: String,
        @Query("aqi") aqi: String? = null
    ): Response<WeatherResponse>
}