package com.example.weather_app.util

import com.google.gson.Gson

object GsonExt {
    fun Gson.toJsonOrNull(obj: Any?) : String {
        return try {
            this.toJson(obj) ?: ""
        } catch (e: Exception) {
            ""
        }
    }
}