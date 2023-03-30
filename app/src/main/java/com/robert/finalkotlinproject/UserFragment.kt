package com.robert.finalkotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView


class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle home click
                    Navigation.findNavController (view).navigate(R.id.action_userFragment_to_homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    // Handle search click
                    Navigation.findNavController (view).navigate(R.id.action_userFragment_to_exploreFragment)
                    true
                }
                R.id.navigation_cart -> {
                    // Handle cart click
                    Navigation.findNavController (view).navigate(R.id.action_userFragment_to_cartFragment)
                    true
                }
                R.id.navigation_user -> {
                    // Handle user click
                    true
                }
                else -> false
            }
        }

        bottomNavigationView?.selectedItemId = R.id.userFragment // C

        return view
    }


}