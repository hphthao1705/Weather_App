package com.example.weather_app.ui.search

import com.example.weather_app.ui.home.data.CountryUiData

sealed class SearchUiState {
    data class Success(val data: List<CountryUiData>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
    object Loading : SearchUiState()
    object StandBy : SearchUiState()
}