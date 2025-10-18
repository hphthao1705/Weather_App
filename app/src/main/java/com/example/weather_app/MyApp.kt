package com.example.weather_app

import android.app.Application
import com.example.weather_app.util.PrefsUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PrefsUtils.init(applicationContext)
    }
}