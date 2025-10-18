package com.example.weather_app.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

class CurlLoggingInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // RequestBody?' is deprecated.
        val method = request.method
        // RequestBody?' is deprecated.
        val body = request.body
        // HttpUrl' is deprecated.
        val url = request.url

        val curlCmd = StringBuilder("curl -X $method")

        // Add body if present
        val requestBody = body
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val bodyString = buffer.readUtf8().replace("\"", "\\\"")
            curlCmd.append(" -d \"$bodyString\"")
        }

        // Add URL
        curlCmd.append(" \"${url}\"")

        Log.d("CURL", curlCmd.toString())

        return chain.proceed(request)
    }

}