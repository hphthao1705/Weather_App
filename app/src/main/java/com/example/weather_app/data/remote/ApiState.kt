package com.example.weather_app.data.remote

sealed class ApiState<out T> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val code: Int, val message: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()
}

fun Nothing?.toUnknownError(): ApiState.Error {
    return ApiState.Error(code = -1, message = "Something went wrong")
}
