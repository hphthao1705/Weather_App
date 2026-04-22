package com.example.weather_app.di

import com.example.weather_app.data.repository.CountryRepository
import com.example.weather_app.data.repository.WeatherRepository
import com.example.weather_app.domain.repository.usecase.CountryUseCase
import com.example.weather_app.domain.repository.usecase.CountryUseCaseImpl
import com.example.weather_app.domain.repository.usecase.WeatherUseCase
import com.example.weather_app.domain.repository.usecase.WeatherUseCaseImpl
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