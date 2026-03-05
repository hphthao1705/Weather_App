package com.example.weather_app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.api.ApiState
import com.example.weather_app.ui.home.data.CountryUiData
import com.example.weather_app.usecase.CountryUseCase
import com.example.weather_app.util.GsonExt.toJsonOrNull
import com.example.weather_app.util.PrefsUtils
import com.example.weather_app.util.debugLog
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: CountryUseCase
) : ViewModel() {

    private val gson by lazy { Gson() }

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState.StandBy)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        getAllCountry()
    }

    fun getAllCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getAllCountry().collect { apiState ->
                when(apiState) {
                    is ApiState.Error -> {
                        "getAllCountry: error - ${apiState.message}".debugLog()
                        _uiState.value = SearchUiState.Error(message = apiState.message)
                    }
                    is ApiState.Loading -> {
                        "getAllCountry: Loading".debugLog()
                        _uiState.value = SearchUiState.Loading
                    }
                        is ApiState.Success -> {
                            "1".debugLog()
                            val countries: List<CountryUiData?>? = apiState.data
                            "2".debugLog()
                            if(!countries.isNullOrEmpty()) {
                                "3".debugLog()
                                _uiState.value = SearchUiState.Success(data = countries.filterNotNull())
                                val json = gson.toJsonOrNull(apiState.data.orEmpty())
                                if(json.isNotEmpty()) {
                                    "4".debugLog()
                                    PrefsUtils.saveCountryList(json = json)
                                }
                            } else {
                                "5".debugLog()
                                _uiState.value = SearchUiState.Error(message = "No data")
                            }
                        }
                }
            }
        }
    }
}