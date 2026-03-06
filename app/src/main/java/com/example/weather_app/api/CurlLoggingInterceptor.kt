package com.example.weather_app.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer

class CurlLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val startTime = System.nanoTime()

        val method = request.method
        val url = request.url
        val headers = request.headers

        val curlCmd = StringBuilder("curl -X $method")

        // headers
        headers.forEach {
            curlCmd.append(" -H \"${it.first}: ${it.second}\"")
        }

        // body
        request.body?.let { requestBody ->
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val bodyString = buffer.readUtf8().replace("\"", "\\\"")
            curlCmd.append(" -d \"$bodyString\"")
        }

        curlCmd.append(" \"$url\"")

        Log.d("API_CURL", curlCmd.toString())

        // proceed request
        val response = chain.proceed(request)

        val endTime = System.nanoTime()
        val durationMs = (endTime - startTime) / 1_000_000

        val responseBody = response.body
        val responseString = responseBody?.string()

        Log.d(
            "API_RESPONSE",
            """
            ─────────────────────────────
            URL: $url
            METHOD: $method
            HEADERS: $headers
            
            RESPONSE CODE: ${response.code}
            TIME: ${durationMs}ms
            
            RESPONSE BODY:
            $responseString
            ─────────────────────────────
            """.trimIndent()
        )

        // Because body.string() can be read only once
        val newResponseBody = responseString?.toResponseBody(responseBody?.contentType())

        return response.newBuilder()
            .body(newResponseBody)
            .build()
    }
}
