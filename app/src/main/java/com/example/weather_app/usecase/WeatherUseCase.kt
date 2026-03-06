package com.example.weather_app.usecase

import WeatherResponse
import com.example.weather_app.api.ApiState
import com.example.weather_app.repository.WeatherRepository
import com.example.weather_app.ui.weatherDetails.data.WeatherUiData
import com.example.weather_app.ui.weatherDetails.data.toWeatherUiData
import com.example.weather_app.util.debugLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface WeatherUseCase {
    suspend fun getWeather(cityName: String?): Flow<ApiState<WeatherUiData>>
}

class WeatherUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : WeatherUseCase {
    override suspend fun getWeather(cityName: String?): Flow<ApiState<WeatherUiData>> {
        return repository.getWeather(cityName = cityName.orEmpty())
            .map { apiState: ApiState<WeatherResponse> ->
                when (apiState) {
                    is ApiState.Success -> {
                        val data = apiState.data
                        "Success: ${apiState.data}".debugLog()

                        ApiState.Success(data.toWeatherUiData())
                    }
                    is ApiState.Error -> ApiState.Error(
                        code = apiState.code,
                        message = apiState.message
                    )

                    is ApiState.Loading -> ApiState.Loading
                }
            }
    }
}