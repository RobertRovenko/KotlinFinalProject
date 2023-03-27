package com.robert.finalkotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    val dataList = listOf(
        MyData("Varor", R.drawable.home),
        MyData("Hem", R.drawable.home),
        MyData("Människa", R.drawable.user),
        MyData("kolla", R.drawable.search),
        MyData("Köp", R.drawable.cart),
        MyData("Människa", R.drawable.user),
        MyData("kolla", R.drawable.search),
        MyData("kolla", R.drawable.search),
        MyData("Tomt", R.drawable.home)

    )

    val adapter = MyAdapter(dataList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        adapter.setOnItemClickListener(0) { data ->
            // Perform action based on the clicked data object
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(1) { data ->
            // Perform action based on the clicked data object
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(2) { data ->
            // Perform action based on the clicked data object
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(3) { data ->
            // Perform action based on the clicked data object
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(4) { data ->
            // Perform action based on the clicked data object
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(5) { data ->
            // Perform action based on the clicked data object
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(6) { data ->
            // Perform action based on the clicked data object
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(7) { data ->
            // Perform action based on the clicked data object
            Toast.makeText(requireContext(), "Clicked item: ${data.text}", Toast.LENGTH_SHORT).show()
        }
        adapter.setOnItemClickListener(8) { data ->
            // Perform action based on the clicked data object
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