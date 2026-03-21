package com.example.weather_app.util

import android.content.Context
import android.widget.Toast
import com.example.weather_app.R
import com.example.weather_app.ui.search.state.ErrorType

data object WeatherToast {
    operator fun invoke(context: Context?, message: String? = null, duration: Int = Toast.LENGTH_SHORT) {
        context?.let { ctx ->
            val text = message ?: ctx.getString(R.string.something_went_wrong)
            Toast.makeText(ctx, text, duration).show()
        } ?: return
    }

    fun showError(
        context: Context?,
        errorType: ErrorType,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        context?.let { ctx ->
            val text = when (errorType) {
                ErrorType.NO_INTERNET -> ctx.getString(R.string.no_internet)
                ErrorType.NO_DATA -> ctx.getString(R.string.something_went_wrong)
            }
            Toast.makeText(ctx, text, duration).show()
        } ?: return
    }
}
