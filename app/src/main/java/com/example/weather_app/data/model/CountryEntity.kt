package com.example.weather_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val flagResourceName: String
)
