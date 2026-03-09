package com.example.weather_app.repository

import com.example.weather_app.api.CountryApiClient
import com.example.weather_app.api.ApiHelper
import com.example.weather_app.api.ApiState
import com.example.weather_app.data.CountryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface CountryRepository {
    suspend fun getAllCountry() : Flow<ApiState<List<CountryResponse>>>
}

class CountryRepositoryImpl @Inject constructor(
    private val api: CountryApiClient,
) : CountryRepository {
    override suspend fun getAllCountry(): Flow<ApiState<List<CountryResponse>>> = flow {
        emit(ApiState.Loading)
        ApiHelper.launch (
            apiCall = {
                api.getAllCountry()
            },
            onSuccess = { response ->
                if(!response.isNullOrEmpty()) {
                    emit(ApiState.Success(response))
                } else {
                    emit(ApiState.Error(code = -1, message = "No Data"))
                }
            },
            onFailure = {
                emit(ApiState.Error(
                    code = it.errorCode ?: -1,
                    message = it.errorMessage ?: "Something went wrong. Please contact to support"
                ))
            }
        )
    }
}
