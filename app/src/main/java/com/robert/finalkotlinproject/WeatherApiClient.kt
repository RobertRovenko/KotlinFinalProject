package com.robert.finalkotlinproject

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException

class WeatherApiClient(private val apiKey: String) {
    /*
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    fun getWeatherForCity(cityName: String, callback: (String?, Throwable?) -> Unit) {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=a996df0d8bc9c13f9b0e9bc81a92314a"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body?.string()
                val error = responseString?.let {
                    if (response.isSuccessful) {
                        null
                    } else {
                        JSONObject(it).getString("message")
                    }
                }
                callback(responseString, error?.let { Throwable(it) })
            }
        })
    }*/
}