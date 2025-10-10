package com.example.weather_app.di

import com.example.weather_app.repository.CountryRepository
import com.example.weather_app.usecase.CountryUseCase
import com.example.weather_app.usecase.CountryUseCaseImpl
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
}