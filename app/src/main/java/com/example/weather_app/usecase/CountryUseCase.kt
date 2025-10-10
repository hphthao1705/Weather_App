package com.example.weather_app.usecase

import com.example.weather_app.api.ApiState
import com.example.weather_app.repository.CountryRepository
import com.example.weather_app.ui.country.uidata.CountryUiData
import com.example.weather_app.ui.country.uidata.toCountryUiDataList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CountryUseCase {
    suspend fun getAllCountry() : Flow<ApiState<List<CountryUiData?>?>>
}

class CountryUseCaseImpl @Inject constructor(
    private val repository: CountryRepository
) : CountryUseCase {
    override suspend fun getAllCountry(): Flow<ApiState<List<CountryUiData?>?>> {
        return repository.getAllCountry().map { apiState ->
            when(apiState) {
                is ApiState.Success -> {
                    val mappedData = apiState.data.toCountryUiDataList()
                    ApiState.Success(mappedData)
                }

                is ApiState.Error -> ApiState.Error(
                    code = apiState.code,
                    message = apiState.message
                )
                is ApiState.Loading -> ApiState.Loading
            }
        }
    }
}