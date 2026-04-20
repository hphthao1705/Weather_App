package com.example.weather_app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val countryName: String,
    val countryFlag: String
)
