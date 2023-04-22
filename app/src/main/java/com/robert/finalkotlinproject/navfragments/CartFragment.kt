package com.robert.finalkotlinproject.navfragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.robert.finalkotlinproject.ProductAdapter
import com.robert.finalkotlinproject.R
import com.robert.finalkotlinproject.cartlogic.Cart
import com.robert.finalkotlinproject.cartlogic.CartViewModel


class CartFragment : Fragment(){

    private lateinit var totalCostTextView: TextView
    private lateinit var checkOutButton: Button
    private lateinit var productAdapter: ProductAdapter
    private lateinit var discountCodeEditText: EditText
    private lateinit var applyButton: Button
    private lateinit var emptyCartTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Call the super.onCreateView method
        super.onCreateView(inflater, container, savedInstanceState)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerView = view.findViewById(R.id.cartRecyclerView)
        totalCostTextView = view.findViewById(R.id.total_cost_textview)
        checkOutButton = view.findViewById(R.id.pay_button)
        discountCodeEditText = view.findViewById(R.id.discount_code_edittext)
        applyButton = view.findViewById(R.id.apply_discount_button)
        emptyCartTextView = view.findViewById(R.id.empty_cart_textview)
        bottomNavigationView = view.findViewById(R.id.bottom_navigation)

        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        productAdapter = ProductAdapter(cartViewModel.products.value ?: emptyList(), cartViewModel)

        // Get the list of products from the Cart object
        val products = Cart.products

        // Get the RecyclerView from the layout and set its layout manager
        val recyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(products, cartViewModel)
        recyclerView.adapter = productAdapter
        val viewModel: CartViewModel by activityViewModels()


        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }

        cartViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.products = products
            productAdapter.notifyDataSetChanged()
        }

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
            // Dismiss the keyboard
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)

            // Stop the text edit from flashing
            applyButton.clearFocus()
            discountCodeEditText.clearFocus()


            // Rest of your code...
            if (viewModel.discountCode == "5off" && !viewModel.discountUsed) {
                viewModel.applyDiscount()
                Toast.makeText(requireContext(), "Discount applied", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Discount code is invalid", Toast.LENGTH_SHORT).show()
            }

            discountCodeEditText.setText("")
        }

        checkOutButton.setOnClickListener{
            viewModel.resetDiscount()

            //Navigation.findNavController (view).navigate(R.id.action_cartFragment_to_checkoutFragment)
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