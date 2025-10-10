package com.example.weather_app.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigationevent.NavigationEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel: ViewModel() {
//    private val _navEvents = MutableSharedFlow<NavEvent>()
//    val navEvents = _navEvents.asSharedFlow()
//
//    fun onLoginClick() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _navEvents.emit(NavEvent.GoToOnBoardingScreen)
//        }
//    }
//
//    fun onBack() {
//        viewModelScope.launch {
//            _navEvents.emit(NavEvent.GoBack)
//        }
//    }
}