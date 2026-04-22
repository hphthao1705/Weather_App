package com.example.weather_app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.remote.ApiState
import com.example.weather_app.data.local.dao.CountryDao
import com.example.weather_app.data.local.dao.entities.CountryEntity
import com.example.weather_app.ui.home.data.CountryUiData
import com.example.weather_app.ui.search.state.ErrorType
import com.example.weather_app.ui.search.state.SearchUiState
import com.example.weather_app.domain.repository.usecase.CountryUseCase
import com.example.weather_app.util.GsonExt.toJsonOrNull
import com.example.weather_app.util.PrefsUtils
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
    private val useCase: CountryUseCase,
    private val countryDao: CountryDao
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
                when (apiState) {
                    is ApiState.Error -> {
                        when(apiState.code == -1) {
                            true -> {
                                // NO INTERNET
                                _uiState.value = SearchUiState.Error(
                                    title = "No Internet!",
                                    content = "Unable to fetch weather data. Please check your connection and try again.",
                                    errorType = ErrorType.NO_INTERNET
                                )

                                // delete the country cache
                                PrefsUtils.deleteCountryList()
                            }

                            else -> {
                                _uiState.value = SearchUiState.Error(
                                    title = "No Data Available",
                                    content = "No country data is available right now.",
                                    errorType = ErrorType.NO_DATA
                                )
                            }
                        }
                    }

                    is ApiState.Loading -> {
                        _uiState.value = SearchUiState.Loading
                    }

                    is ApiState.Success -> {
                        val countries: List<CountryUiData?>? = apiState.data
                        if (!countries.isNullOrEmpty()) {
                            _uiState.value = SearchUiState.Success(data = countries.filterNotNull())
                            val json = gson.toJsonOrNull(apiState.data.orEmpty())
                            if (json.isNotEmpty()) {
                                PrefsUtils.saveCountryList(json = json)
                            }
                        } else {
                            _uiState.value = SearchUiState.Error(
                                title = "No Data Available",
                                content = "No country data is available right now.",
                                errorType = ErrorType.NO_DATA
                            )
                        }
                    }
                }
            }
        }
    }

    fun insertCountry(country: CountryUiData) {
        viewModelScope.launch(Dispatchers.IO) {
            val isSuccess = countryDao.deleteCountriesByName(country.name.orEmpty())
            if (isSuccess > 0) {
                // delete success > insert new country
                countryDao.insert(
                    CountryEntity(
                        countryName = country.name.orEmpty(),
                        countryFlag = country.flags.orEmpty()
                    )
                )
            }
        }
    }

    fun filterCountryByName(query: String? = null, isShowAll: Boolean? = false) {
        val countryJson = PrefsUtils.getCountryList()
        if(countryJson.isBlank()) {
            getAllCountry()
            return
        }

        val countries = gson.fromJson(countryJson, Array<CountryUiData>::class.java).toList()

        if(isShowAll == true) {
            _uiState.value = SearchUiState.Success(data = countries)
            return
        }

        val filteredCountries = countries.filter {
            it.name?.contains(query?.trim().orEmpty(), ignoreCase = true) == true
        }

        if(filteredCountries.isNotEmpty()) {
            _uiState.value = SearchUiState.Success(data = filteredCountries)
        } else {
            _uiState.value = SearchUiState.Error(
                title = "No Matching Location",
                content = "No results for $query. Please check the spelling or try another place.",
                errorType = ErrorType.NO_DATA
            )
        }
    }
}