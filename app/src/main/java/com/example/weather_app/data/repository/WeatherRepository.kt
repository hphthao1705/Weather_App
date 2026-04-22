package com.example.weather_app.data.repository

import WeatherResponse
import com.example.weather_app.data.remote.ApiHelper
import com.example.weather_app.data.remote.ApiState
import com.example.weather_app.data.remote.WeatherApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getWeather(cityName: String) : Flow<ApiState<WeatherResponse>>
}

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiClient,
) : WeatherRepository {
    override suspend fun getWeather(cityName: String): Flow<ApiState<WeatherResponse>> = flow {
        emit(ApiState.Loading)
        ApiHelper.launch (
            apiCall = {
                api.getWeather(cityName = cityName)
            },
            onSuccess = { response: WeatherResponse? ->
                if(response != null) {
                    emit(ApiState.Success(response))
                } else {
                    emit(ApiState.Error(code = -1, message = "No Data"))
                }
            },
            onFailure = {
                emit(ApiState.Error(
                    code = it.errorCode ?: -1,
                    message = it.errorMessage ?: "Something went wrong. Please contact to support"
                ))
            }
        )
    }
}