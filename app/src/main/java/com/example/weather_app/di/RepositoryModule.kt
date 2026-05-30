package com.example.weather_app.di

import com.example.weather_app.data.remote.CountryApiClient
import com.example.weather_app.data.remote.WeatherApiClient
import com.example.weather_app.data.network.NetworkMonitor
import com.example.weather_app.data.repository.CountryRepository
import com.example.weather_app.data.repository.CountryRepositoryImpl
import com.example.weather_app.data.repository.WeatherRepository
import com.example.weather_app.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCountryRepository(
        apiService: CountryApiClient,
        networkMonitor: NetworkMonitor
    ): CountryRepository {
        return CountryRepositoryImpl(api = apiService, networkMonitor = networkMonitor)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiService: WeatherApiClient
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }
}