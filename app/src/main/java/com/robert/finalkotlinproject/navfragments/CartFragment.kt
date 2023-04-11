package com.robert.finalkotlinproject.navfragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var checkOutButton: Button
    private lateinit var productAdapter: ProductAdapter
    private lateinit var discountCodeEditText: EditText
    private lateinit var applyButton: Button
    private lateinit var emptyCartTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private var totalCost: Double = 0.0
    private var discountCode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Call the super.onCreateView method
        super.onCreateView(inflater, container, savedInstanceState)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        totalCostTextView = view.findViewById(R.id.total_cost_textview)
        checkOutButton = view.findViewById(R.id.pay_button)
        discountCodeEditText = view.findViewById<EditText>(R.id.discount_code_edittext)
        applyButton = view.findViewById<Button>(R.id.apply_discount_button)
        emptyCartTextView = view.findViewById(R.id.empty_cart_textview)
        bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)


        // Get the RecyclerView from the layout and set its layout manager
        val recyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(Cart.products)
        recyclerView.adapter = productAdapter

        val viewModel: CartViewModel by activityViewModels()
        checkOutButton = view.findViewById(R.id.pay_button)

        // Initialize the totalCostTextView
        totalCostTextView = view.findViewById(R.id.total_cost_textview)
        viewModel.totalCost.observe(viewLifecycleOwner) { totalCost ->
            var discountedCost = totalCost
            if (viewModel.discountCode == "5off") {
                discountedCost *= 0.95 // Apply 5% discount
            }
            totalCostTextView.text = "$${discountedCost}"
        }



        // Observe the cart items and show/hide the appropriate views
        viewModel.products.observe(viewLifecycleOwner) { cartItems ->
            if (cartItems.isEmpty()) {
                recyclerView.visibility = View.GONE
                totalCostTextView.visibility = View.GONE
                checkOutButton.visibility = View.GONE
                discountCodeEditText.visibility = View.GONE
                applyButton.visibility = View.GONE
                emptyCartTextView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                totalCostTextView.visibility = View.VISIBLE
                checkOutButton.visibility = View.VISIBLE
                discountCodeEditText.visibility = View.VISIBLE
                applyButton.visibility = View.VISIBLE
                emptyCartTextView.visibility = View.GONE
            }
        }


        val discountCodeEditText = view.findViewById<EditText>(R.id.discount_code_edittext)


        discountCodeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let { viewModel.setDiscountCode(it) }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        viewModel.discountedTotalCost.observe(viewLifecycleOwner) { discountedTotalCost ->
            totalCostTextView.text = "$${discountedTotalCost}"
        }
        val applyButton = view.findViewById<Button>(R.id.apply_discount_button)
        applyButton.setOnClickListener {
            if (viewModel.discountCode == "5off" && !viewModel.discountUsed) {
                viewModel.applyDiscount()
                Toast.makeText(requireContext(), "Discount applied", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Discount has already been used or is invalid", Toast.LENGTH_SHORT).show()
            }
        }

        checkOutButton.setOnClickListener{
            viewModel.resetDiscount()
            Toast.makeText(context, "Discount code has been reset", Toast.LENGTH_SHORT).show()
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