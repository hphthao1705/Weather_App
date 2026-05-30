package com.example.weather_app.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.remote.ApiState
import com.example.weather_app.data.local.dao.CountryDao
import com.example.weather_app.data.model.Country
import com.example.weather_app.domain.repository.usecase.CountryUseCase
import com.example.weather_app.util.GsonExt.toJsonOrNull
import com.example.weather_app.util.PrefsUtils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: CountryUseCase,
    private val countryDao: CountryDao
) : ViewModel() {

    private val gson by lazy { Gson() }

    private val _historyCountryData: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())
    val historyCountryData = _historyCountryData.asStateFlow()

    init {
        getHistoryCountries()
        getAllCountry()
    }

    private fun getHistoryCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            countryDao.getAllCountries().collect { countries ->
                _historyCountryData.update { countries }
            }
        }
    }

    fun getAllCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getAllCountry().collect { apiState ->
                when(apiState) {
                    is ApiState.Error -> Log.d("rabbit", "Error: ${apiState.message}")
                    is ApiState.Loading -> Log.d("rabbit", "Loading")
                    is ApiState.Success -> {
                        val json = gson.toJsonOrNull(apiState.data.orEmpty())
                        if(json.isNotEmpty()) {
                            PrefsUtils.saveCountryList(json = json)
                        }
                    }
                }
            }
        }
    }
}
