package com.example.weather_app.di

import com.example.weather_app.repository.CountryRepository
import com.example.weather_app.repository.WeatherRepository
import com.example.weather_app.usecase.CountryUseCase
import com.example.weather_app.usecase.CountryUseCaseImpl
import com.example.weather_app.usecase.WeatherUseCase
import com.example.weather_app.usecase.WeatherUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideCountryUseCase(
        repository: CountryRepository
    ): CountryUseCase {
        return CountryUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(
        repository: WeatherRepository
    ): WeatherUseCase {
        return WeatherUseCaseImpl(repository)
    }
}