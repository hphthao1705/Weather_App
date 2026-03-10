package com.example.weather_app.di

import com.example.weather_app.api.CountryApiClient
import com.example.weather_app.api.CurlLoggingInterceptor
import com.example.weather_app.api.WeatherApiClient
import com.example.weather_app.network.NetWorkInterface
import com.example.weather_app.network.NetworkMonitor
import com.example.weather_app.util.AppModule.COUNTRY_BASE_URL
import com.example.weather_app.util.AppModule.WEATHER_API_VERSION
import com.example.weather_app.util.AppModule.WEATHER_BASE_URL
import com.google.firebase.appdistribution.gradle.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

// create qualifier to distinct retrofit
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CountryRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(CurlLoggingInterceptor())
            .build()
    }

    @CountryRetrofit
    @Singleton
    @Provides
    fun provideRetrofitForCountry(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("$COUNTRY_BASE_URL$COUNTRY_API_VERSION")
            .baseUrl(COUNTRY_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @WeatherRetrofit
    @Singleton
    @Provides
    fun provideRetrofitForWeather(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$WEATHER_BASE_URL$WEATHER_API_VERSION")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryApiClient(@CountryRetrofit retrofit: Retrofit): CountryApiClient =
        retrofit.create(CountryApiClient::class.java)

    @Provides
    @Singleton
    fun provideWeatherApiClient(@WeatherRetrofit retrofit: Retrofit): WeatherApiClient =
        retrofit.create(WeatherApiClient::class.java)

    @Provides
    @Singleton
    fun provideNetworkMonitor(monitor: NetworkMonitor): NetWorkInterface = monitor
}