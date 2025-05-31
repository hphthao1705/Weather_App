package com.example.weather_app

enum class NavigationScreen {
    ON_BOARDING_SCREEN,
    HOME_SCREEN,
}

sealed class ScreenState {
    data object StandBy: ScreenState()
    data object Loading: ScreenState()
    data class NavigateTo(val route: String) : ScreenState()
}