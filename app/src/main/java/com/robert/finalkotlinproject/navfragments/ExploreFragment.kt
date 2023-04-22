package com.robert.finalkotlinproject.navfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.robert.finalkotlinproject.MyData
import com.robert.finalkotlinproject.R


class ExploreFragment : Fragment() {


    val dataList = listOf(
        MyData("Yves Saint-Laurent \nBleu Électrique", "85 $","50 ml", R.drawable.ysl, ),
        MyData("Versace Eros","90 $", "90 ml", R.drawable.versaceeros ),
        MyData("Dior Sauvage", "120 $","100 ml", R.drawable.diorsauvage),
        MyData("Giorgio Armani \nProfumo", "80 $","50 ml", R.drawable.gioprofumo ),
        MyData("Dior \nMiss Dior", "110 $", "100 ml", R.drawable.missdior ),
        MyData("Versace Eros \nFemme", "95 $","80 ml", R.drawable.versacew ),
        MyData("Viktor & Rolf \nFlowerbomb", "110 $","90 ml", R.drawable.flowerbomb),
        MyData("Yves Saint-Laurent \nLibre", "110 $","100 ml", R.drawable.yslw ),
        MyData("", "","", R.drawable.white)

    )

    val adapter = MyAdapter(dataList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_explore, container, false)

        adapter.setOnItemClickListener(0) {

            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_exploreFragment_to_yslbleuelec)

        }
        adapter.setOnItemClickListener(1) {

            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_exploreFragment_to_versaceErosFlame)

        }
        adapter.setOnItemClickListener(2) {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_exploreFragment_to_diorSauvage)

        }
        adapter.setOnItemClickListener(3) {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_exploreFragment_to_giorgioArmaniProfumo)

        }
        adapter.setOnItemClickListener(4) {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_exploreFragment_to_missDior)

        }
        adapter.setOnItemClickListener(5) {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_exploreFragment_to_versaceErosFemme)

        }
        adapter.setOnItemClickListener(6) {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_exploreFragment_to_giorgioArmaniMyWay)

        }
        adapter.setOnItemClickListener(7) {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_exploreFragment_to_yslLibre)

        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle home click
                    Navigation.findNavController (view).navigate(R.id.action_exploreFragment_to_homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    // Handle search click
                    true
                }
                R.id.navigation_cart -> {
                    // Handle cart click
                    Navigation.findNavController (view).navigate(R.id.action_exploreFragment_to_cartFragment)
                    true
                }
                R.id.navigation_user -> {
                    // Handle user click
                    Navigation.findNavController (view).navigate(R.id.action_exploreFragment_to_userFragment)
                    true
                }
                else -> false
            }
        }

        bottomNavigationView?.selectedItemId = R.id.exploreFragment
        // Change this to the appropriate item ID for each fragment

        return view
    }


}