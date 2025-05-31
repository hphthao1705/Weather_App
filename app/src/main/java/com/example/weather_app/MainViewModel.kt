package com.example.weather_app

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.StandBy)
    val screenState = _screenState.asStateFlow()

    //STATE
    fun updateScreenState(route: String) {
        _screenState.update { ScreenState.NavigateTo(route) }
    }
}