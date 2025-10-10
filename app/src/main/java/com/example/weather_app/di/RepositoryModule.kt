package com.example.weather_app.di

import com.example.weather_app.api.ApiClient
import com.example.weather_app.repository.CountryRepository
import com.example.weather_app.repository.CountryRepositoryImpl
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
        apiService: ApiClient
    ): CountryRepository {
        return CountryRepositoryImpl(apiService)
    }
}