package com.example.weather_app.ui.weatherDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.api.ApiState
import com.example.weather_app.ui.weatherDetails.state.WeatherUiState
import com.example.weather_app.usecase.WeatherUseCase
import com.example.weather_app.util.debugLog
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCase: WeatherUseCase
) : ViewModel() {

    private val gson by lazy { Gson() }
    private val _uiState: MutableStateFlow<WeatherUiState> = MutableStateFlow(WeatherUiState.StandBy)
    val uiState = _uiState.asStateFlow()

    fun getWeather(cityName: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getWeather(cityName = cityName).collect { apiState ->
                when(apiState) {
                    is ApiState.Error -> _uiState.value = WeatherUiState.Error(apiState.message)
                    is ApiState.Loading -> _uiState.value = WeatherUiState.Loading
                    is ApiState.Success -> {
                        "Success: ${apiState.data}".debugLog()
                        _uiState.value = WeatherUiState.Success(apiState.data)
                    }
                }
            }
        }
    }
}
