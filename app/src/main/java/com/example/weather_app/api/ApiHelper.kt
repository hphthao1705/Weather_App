package com.example.weather_app.api

import com.example.weather_app.util.printDebugStackTrace
import retrofit2.Response

object ApiHelper {
    suspend fun <T> launch(
        apiCall: suspend () -> Response<T>,
        onSuccess: suspend (T?) -> Unit,
        onFailure: suspend (ApiError) -> Unit
    ) {
        try {
            val result = apiCall()
            if (result.isSuccessful) {
                onSuccess(result.body())
            } else {
                onFailure(ApiError(errorCode = result.code(), errorMessage = result.message()))
            }
        } catch (e: Exception) {
            e.printDebugStackTrace()
            onFailure(ApiError(errorCode = 400, errorMessage = e.message))
        }
    }
}


class ApiError(
    val errorCode: Int?,
    val errorMessage: String?
)