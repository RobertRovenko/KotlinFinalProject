package com.robert.finalkotlinproject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException


class WeatherApiClient(private val apiKey: String) {

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
    }


    suspend fun getWeather(city: String, apiKey: String): Weather? {
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=a996df0d8bc9c13f9b0e9bc81a92314a"

        return try {
            withContext(Dispatchers.IO) {
                val response =
                    withContext(Dispatchers.Default) {
                        client.newCall(Request.Builder().url(url).build()).execute()
                    }
                val weatherJson = response.body?.string()?.let { JSONObject(it) }
                val mainJson = weatherJson?.getJSONObject("main")
                val temperature = mainJson?.getDouble("temp")
                Weather(temperature)
            }
        } catch (e: Exception) {
            null
        }


    }
}