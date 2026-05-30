package com.example.weather_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_app.data.model.Country
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CountryDao {
    @Query("SELECT * FROM countries")
    abstract fun getAllCountries(): Flow<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(country: Country)

    @Query("DELETE FROM countries WHERE name = :name")
    abstract suspend fun deleteCountriesByName(name: String): Int
}
