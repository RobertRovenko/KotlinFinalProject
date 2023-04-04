package com.robert.finalkotlinproject.navfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.robert.finalkotlinproject.*
import com.robert.finalkotlinproject.cartlogic.Cart
import com.robert.finalkotlinproject.cartlogic.CartViewModel

class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var totalCostTextView: TextView
    private lateinit var productAdapter: ProductAdapter
    private var totalCost: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Call the super.onCreateView method
        super.onCreateView(inflater, container, savedInstanceState)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)


        // Get the RecyclerView from the layout and set its layout manager
        val recyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(Cart.products)
        recyclerView.adapter = productAdapter

        val viewModel: CartViewModel by activityViewModels()

        // Initialize the totalCostTextView
        totalCostTextView = view.findViewById(R.id.total_cost_textview)
        viewModel.totalCost.observe(viewLifecycleOwner) { totalCost ->
            totalCostTextView.text = "$${totalCost}"
        }


        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle home click
                    Navigation.findNavController (view).navigate(R.id.action_cartFragment_to_homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    // Handle search click
                    Navigation.findNavController (view).navigate(R.id.action_cartFragment_to_exploreFragment)
                    true
                }
                R.id.navigation_cart -> {
                    // Handle cart click

                    true
                }
                R.id.navigation_user -> {
                    // Handle user click
                    Navigation.findNavController (view).navigate(R.id.action_cartFragment_to_userFragment)
                    true
                }
                else -> false
            }
        }

        return view
    }

}