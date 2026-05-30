package com.example.weather_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather_app.data.local.dao.CountryDao
import com.example.weather_app.data.model.Country

@Database(entities = [Country::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}
