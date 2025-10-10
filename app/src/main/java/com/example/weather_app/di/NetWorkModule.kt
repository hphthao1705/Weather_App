package com.example.weather_app.di

import com.example.weather_app.api.ApiClient
import com.example.weather_app.api.CurlLoggingInterceptor
import com.example.weather_app.util.AppModule.COUNTRY_BASE_URL
import com.google.firebase.appdistribution.gradle.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    @Singleton
    @Provides
    fun provideRetrofitForCountry(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
//            .baseUrl("$COUNTRY_BASE_URL$COUNTRY_API_VERSION")
            .baseUrl(COUNTRY_BASE_URL)
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
    fun provideApiClient(retrofit: Retrofit): ApiClient = retrofit.create(ApiClient::class.java)
}