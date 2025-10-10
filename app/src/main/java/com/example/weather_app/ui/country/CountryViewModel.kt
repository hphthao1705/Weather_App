package com.example.weather_app.ui.country

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.api.ApiState
import com.example.weather_app.usecase.CountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val useCase: CountryUseCase
) : ViewModel() {

    init {
        getAllCountry()
    }

    fun getAllCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getAllCountry().collect { apiState ->
                // TODO: do something with data
                when(apiState) {
                    is ApiState.Error -> Log.d("rabbit", "Error: ${apiState.message}")
                    is ApiState.Loading -> Log.d("rabbit", "Loading")
                    is ApiState.Success -> Log.d("rabbit", "Success: ${apiState.data}")
                }
            }
        }
    }
}