package com.example.weather_app.util

import android.content.Context
import android.content.SharedPreferences
import com.example.weather_app.util.PrefsValue.PREFS_NAME
import com.example.weather_app.util.PrefsValue.PREFS_COUNTRY
import androidx.core.content.edit

object PrefsValue {
    const val PREFS_NAME = "app_prefs"
    const val PREFS_COUNTRY = "country_prefs"
}
object PrefsUtils {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        // Initialize only once using applicationContext to prevent leaks
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun isInitialized() : Boolean {
        return ::prefs.isInitialized
    }

    fun saveCountryList(json: String) {
        if(isInitialized() && json.isNotEmpty()) {
            prefs.edit { putString(PREFS_COUNTRY, json) }
        }
    }

    fun getCountryList(): String {
        if(isInitialized()) {
            return prefs.getString(PREFS_COUNTRY, "").orEmpty()
        }
        return ""
    }

    fun deleteCountryList() {
        if (isInitialized()) {
            prefs.edit {
                remove(PREFS_COUNTRY)
            }
        }
    }
}
