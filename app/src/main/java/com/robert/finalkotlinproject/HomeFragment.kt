package com.robert.finalkotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    val dataList = listOf(
        MyData("Yves Saint-Laurent \nBleu Électrique", "85 $","Price:", R.drawable.ysl, ),
        MyData("Dior \nMiss Dior", "90 $", "Price:",R.drawable.missdior ),
        MyData("Versace Eros \nFlame","75 $", "Price:",R.drawable.eros ),
        MyData("Versace Eros \nFemme", "85 $","Price:",R.drawable.versacew ),
        MyData("Giorgio Armani \nProfumo", "90 $","Price:",R.drawable.gioprofumo ),
        MyData("Yves Saint-Laurent \nLibre", "100 $","Price:",R.drawable.yslw ),
        MyData("Dior \nSauvage", "120 $","Price:",R.drawable.diorsauvage),
        MyData("Giorgio Armani \nMy Way", "95 $","Price:",R.drawable.myway),
        MyData("", "","",R.drawable.white)

    )

    val adapter = MyAdapter(dataList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        adapter.setOnItemClickListener(0) { data ->

            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_yslbleuelec)
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()

        }
        adapter.setOnItemClickListener(1) { data ->

            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_missDior)
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(2) { data ->
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_versaceErosFlame)
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(3) { data ->
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_versaceErosFemme)
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(4) { data ->
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_giorgioArmaniProfumo)
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(5) { data ->
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_yslLibre)
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(6) { data ->
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_diorSauvage)
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(7) { data ->
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_homeFragment_to_giorgioArmaniMyWay)
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }



        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

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

        bottomNavigationView?.selectedItemId = R.id.navigation_home // Change this to the appropriate item ID for each fragment

        return view
    }
}