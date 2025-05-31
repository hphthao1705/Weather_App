package com.example.weather_app.util

import android.content.Context
import android.widget.Toast
import com.example.weather_app.R

data object WeatherToast {
    operator fun invoke(context: Context?, message: String? = null, duration: Int = Toast.LENGTH_SHORT) {
        context?.let { ctx ->
            val text = message ?: ctx.getString(R.string.something_went_wrong)
            Toast.makeText(ctx, text, duration).show()
        } ?: return
    }
}
