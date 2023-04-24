package com.robert.finalkotlinproject.navfragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.robert.finalkotlinproject.ProductAdapter
import com.robert.finalkotlinproject.R
import com.robert.finalkotlinproject.cartlogic.Cart
import com.robert.finalkotlinproject.cartlogic.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


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
    private lateinit var emptyImage: ImageView

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
        emptyImage = view.findViewById(R.id.emptyCartImage)

        val emailInputEditText = view.findViewById<EditText>(R.id.email_input_edittext)

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
                emailInputEditText.visibility = View.GONE
                emptyImage.visibility = View.VISIBLE

            } else {
                recyclerView.visibility = View.VISIBLE
                totalCostTextView.visibility = View.VISIBLE
                checkOutButton.visibility = View.VISIBLE
                discountCodeEditText.visibility = View.VISIBLE
                applyButton.visibility = View.VISIBLE
                emptyCartTextView.visibility = View.GONE
                emailInputEditText.visibility = View.VISIBLE
                emptyImage.visibility = View.GONE
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

            val email = emailInputEditText.text.toString().trim()

            if (isValidEmail(email)) {

                val discountedTotalCost = viewModel.discountedTotalCost.value ?: 0.0
                showSnackbar(view, email, discountedTotalCost)

                viewModel.emptyCart()

                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(emailInputEditText.windowToken, 0)

                // Clear text
                emailInputEditText.clearFocus()
                emailInputEditText.text = null


                // Add animation
                val imageView = ImageView(requireContext())
                imageView.setImageResource(R.drawable.addedtocartimage)
                imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.visibility = View.VISIBLE
                imageView.animate()
                    .alpha(1.0f)
                    .setDuration(10000)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            imageView.animate()
                                .alpha(0.0f)
                                .setDuration(1000)
                                .setListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator) {
                                        imageView.visibility = View.GONE
                                    }
                                })
                                .start()
                        }
                    })
                    .start()

                val decorView = requireActivity().window.decorView as ViewGroup
                decorView.addView(imageView)


            } else {

                emailInputEditText.error = "Invalid email address"
            }

            emailInputEditText.clearFocus()
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

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex(pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        return emailRegex.matches(email)
    }


    private fun showSnackbar(view: View, email: String, discountedTotalCost: Double) {
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val session = Session.getInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication("fragranceshopsweden@gmail.com", "uqmatidcdcwesqsd")
                    }
                })

                val message = MimeMessage(session)
                message.setFrom(InternetAddress("fragranceshopsweden@gmail.com"))
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email))
                message.subject = "Order Confirmation"
                message.setText("Thank you for shopping at Fragrance Shop Sweden!\n" + "You total is $discountedTotalCost $\n\n\n" +
                        "Get 5 % off your next order with the code - 5off - \n\n\nContact us here anything is needed\n" +
                        "Fragrance Shop Sweden")
                Transport.send(message)

                withContext(Dispatchers.Main) {
                    val snackbar = Snackbar.make(
                        view,
                        "Order Confirmed!" +
                                " $email",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("Undo") {

                        // Handle undo action

                    }
                    snackbar.show()
                }
            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }

}