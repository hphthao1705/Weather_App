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

    private val backStack = ArrayDeque<NavEventState>().apply {
        add(NavEventState.GoToOnBoarding)
    }

    private fun navigateTo(screen: NavEventState) {
        viewModelScope.launch(Dispatchers.IO) {
            backStack.addLast(screen)
            _navEvents.emit(screen)
        }
    }

    fun goBack(): Boolean {
        if (backStack.size <= 1) return false // nothing to pop
        backStack.removeLast()
        viewModelScope.launch(Dispatchers.IO) {
            _navEvents.emit(backStack.last())
        }
        return true
    }

    fun onGoToOnBoarding() = navigateTo(NavEventState.GoToOnBoarding)
    fun onGoToHome() = navigateTo(NavEventState.GoToHome)
    fun onGoToLogin() = navigateTo(NavEventState.GoToLogin)
    fun onGoToSearch() = navigateTo(NavEventState.GoToSearch)
    fun onGoToWeatherDetails(cityName: String?) = navigateTo(NavEventState.GoToWeatherDetails(cityName))

    fun resetFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            _navEvents.emit(NavEventState.GoToOnBoarding)
        }
    }
}