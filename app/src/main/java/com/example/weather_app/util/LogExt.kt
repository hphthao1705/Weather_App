package com.example.weather_app.util

import android.util.Log

fun Exception.printDebugStackTrace() = Log.e("weather_app_error", this.stackTraceToString())

fun String.debugLog() = Log.d("rabbit", this)
