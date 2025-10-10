package com.example.weather_app.api

import com.example.weather_app.api.ApiEndPoint.GET_ALL_COUNTRY
import com.example.weather_app.data.CountryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

const val COUNTRY_API_KEY = "xEFf3OXcOXy0KsRCWzlVMQ==o18XyHGnWMgIviiF"

interface ApiClient {
    @GET(GET_ALL_COUNTRY)
    suspend fun getAllCountry(
        @Header("X-Api-Key") key: String = COUNTRY_API_KEY
    ): Response<List<CountryResponse>>
}