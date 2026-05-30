package com.example.weather_app

sealed class NavEventState(val order: Int) {
    object GoToOnBoarding : NavEventState(1)
    object GoToLogin: NavEventState(2)
    object GoToHome: NavEventState(3)
    object GoToSearch: NavEventState(4)
    data class GoToWeatherDetails(val cityName: String?) : NavEventState(5)
}