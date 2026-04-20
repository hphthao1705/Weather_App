package com.example.weather_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather_app.data.entities.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CountryDao {
    @Query("SELECT * FROM countries")
    abstract fun getAllCountries(): Flow<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(country: CountryEntity)

    @Query("DELETE FROM countries WHERE countryName = :name")
    abstract suspend fun deleteCountriesByName(name: String)
}
