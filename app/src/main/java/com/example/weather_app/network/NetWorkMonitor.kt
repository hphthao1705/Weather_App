package com.example.weather_app.network

import android.content.Context
import com.example.weather_app.util.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetWorkInterface {
    override val isConnected: Boolean
        get() = NetworkUtils.hasInternetConnection(context)
}