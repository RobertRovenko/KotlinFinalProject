package com.robert.finalkotlinproject.navfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.robert.finalkotlinproject.R
import com.robert.finalkotlinproject.Weather
import com.robert.finalkotlinproject.WeatherApiClient
import kotlinx.coroutines.*


class HomeFragment : Fragment() {

    private lateinit var bannerImageView: ImageButton
    private val weatherApiClient = WeatherApiClient("a996df0d8bc9c13f9b0e9bc81a92314a")

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        bannerImageView = view.findViewById(R.id.bannerimage)


        val newsButton = view.findViewById<Button>(R.id.news_button)
        val signUpButton = view.findViewById<Button>(R.id.sign_up_button)
        val exploreButton = view.findViewById<Button>(R.id.explore_button)
        val menProduct1 = view.findViewById<ImageButton>(R.id.men_product1)
        val menProduct2 = view.findViewById<ImageButton>(R.id.men_product2)
        val menProduct3 = view.findViewById<ImageButton>(R.id.men_product3)
        val womenProduct1 = view.findViewById<ImageButton>(R.id.women_product1)
        val womenProduct2 = view.findViewById<ImageButton>(R.id.women_product2)
        val womenProduct3 = view.findViewById<ImageButton>(R.id.women_product3)

        newsButton.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_newsFragment)
        }

        signUpButton.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_userFragment)
        }

        exploreButton.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_exploreFragment)
        }

        menProduct1.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_versaceErosFlame)
        }
        menProduct2.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_yslbleuelec)
        }
        menProduct3.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_diorSauvage)
        }
        womenProduct1.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_missDior)
        }
        womenProduct2.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_versaceErosFemme)
        }
        womenProduct3.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_yslLibre)
        }


        GlobalScope.launch {
            val apiKey = "a996df0d8bc9c13f9b0e9bc81a92314a"
            val city = "Stockholm"
            val temperature = weatherApiClient.getWeather(city, apiKey)
            withContext(Dispatchers.Main) {
                updateBannerImage(temperature)
            }
        }



        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle home click
                    true
                }
                R.id.navigation_search -> {
                    // Handle search click
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_exploreFragment)
                    true
                }
                R.id.navigation_cart -> {
                    // Handle cart click
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_cartFragment)
                    true
                }
                R.id.navigation_user -> {
                    // Handle user click
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_userFragment)
                    true
                }
                else -> false
            }
        }

        bottomNavigationView?.selectedItemId =
            R.id.navigation_home // Change this to the appropriate item ID for each fragment

        return view
    }


    private fun updateBannerImage(weather: Weather?) {
        println(weather)
        val temperatureCelsius = weather?.temperature?.minus(273.15) ?: -1000.0
        val bannerImageId = when {
            weather == null -> R.drawable.defaultbanner
            temperatureCelsius <= 0 -> R.drawable.yslwinterbanner
            temperatureCelsius >= 15 -> R.drawable.sauvagebanner1
            else -> R.drawable.armanibanner
        }
        bannerImageView.setImageResource(bannerImageId)
    }

}