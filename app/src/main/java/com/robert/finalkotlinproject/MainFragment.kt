package com.robert.finalkotlinproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject


class MainFragment : Fragment() {

    private val weatherApiClient = WeatherApiClient("a996df0d8bc9c13f9b0e9bc81a92314a")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)


       /* weatherApiClient.getWeatherForCity("Stockholm") { response, error ->
            if (error != null) {
                Log.e(TAG, "Error fetching weather data", error)
            } else {
                val weatherData = JSONObject(response!!)
                val temperature = weatherData.getJSONObject("main").getDouble("temp")
                val weatherDescription = weatherData.getJSONArray("weather").getJSONObject(0).getString("description")
                val weatherText = view.findViewById<TextView>(R.id.weatherText)
                updateWeatherText(view.findViewById(R.id.weatherText), weatherDescription)
                 updateWeatherImage(temperature)
            }
        }*/

        val validateButton = view.findViewById<Button>(R.id.validateMail)
        val email = view.findViewById<EditText>(R.id.emailEditText).text.toString()

         // Change this to the appropriate item ID for each fragment

        return view
    }
   /* private fun updateWeatherImage(temperature: Double) {
        val weatherImage = view?.findViewById<ImageView>(R.id.weatherImage)
        if (weatherImage != null) {
            when {
                temperature < 0 -> weatherImage.setImageResource(R.drawable.weather_image_cold)
                temperature < 20 -> weatherImage.setImageResource(R.drawable.weather_image_mild)
                else -> weatherImage.setImageResource(R.drawable.weather_image_warm)
            }
        }
    }*/
    private fun updateWeatherText(weatherText: TextView, description: String) {
        activity?.runOnUiThread {
            weatherText.text = "The weather is $description"
        }
    }

    companion object {
        private const val TAG = "MainFragment"
    }

}