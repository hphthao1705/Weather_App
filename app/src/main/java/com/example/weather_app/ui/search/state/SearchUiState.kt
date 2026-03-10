package com.example.weather_app.ui.search.state

import com.example.weather_app.ui.home.data.CountryUiData

sealed class SearchUiState {
    data class Success(val data: List<CountryUiData>) : SearchUiState()
    data class Error(val title: String, val content: String, val errorType: ErrorType) : SearchUiState()
    object Loading : SearchUiState()
    object StandBy : SearchUiState()
}

enum class ErrorType {
    NO_INTERNET,
    NO_DATA
}