package com.example.weather_app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.weather_app.util.PrefsUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class MyWeatherApp() : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        PrefsUtils.init(applicationContext)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(
                            chain.request().newBuilder()
                                .header("User-Agent", "${packageName}/1.0 (Android; Coil)")
                                .build()
                        )
                    }
                    .build()
            }
            .build()
    }
}