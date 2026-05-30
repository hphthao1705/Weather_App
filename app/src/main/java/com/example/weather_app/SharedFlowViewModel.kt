package com.example.weather_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedFlowViewModel @Inject constructor(): ViewModel() {
    private val _navEvents = MutableStateFlow<NavEventState>(NavEventState.GoToOnBoarding)
    val navEvents = _navEvents.asStateFlow()

    fun onGoToOnBoarding() {
        viewModelScope.launch(Dispatchers.IO) {
            _navEvents.emit(NavEventState.GoToOnBoarding)
        }
    }

    fun onGoToHome() {
        viewModelScope.launch(Dispatchers.IO) {
            _navEvents.emit(NavEventState.GoToHome)
        }
    }

    fun onGoToLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            _navEvents.emit(NavEventState.GoToLogin)
        }
    }

    fun onGoToSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            _navEvents.emit(NavEventState.GoToSearch)
        }
    }

    fun onGoToWeatherDetails(cityName: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _navEvents.emit(NavEventState.GoToWeatherDetails(cityName))
        }
    }

    fun resetFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            _navEvents.emit(NavEventState.GoToOnBoarding)
        }
    }
}