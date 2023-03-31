package com.robert.finalkotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView


class UserFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var logOutButton: Button

    private var isLoggedIn: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)


        emailEditText = view.findViewById(R.id.et_email)
        passwordEditText = view.findViewById(R.id.et_password)

        signUpButton = view.findViewById(R.id.btn_sign_up)
        logOutButton = view.findViewById(R.id.btn_log_out)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // TODO: Create user account with the email and password

            isLoggedIn = true
            updateUI()
        }
        // Set click listener for the log out button
        logOutButton.setOnClickListener {
            // TODO: Log out the user

            isLoggedIn = false
            updateUI()
        }

        // Update UI based on login status
        updateUI()



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

    private fun updateUI() {
        if (isLoggedIn) {
            val titleTextView = view?.findViewById<TextView>(R.id.titleTextView)
            emailEditText.visibility = View.GONE
            passwordEditText.visibility = View.GONE
            signUpButton.visibility = View.GONE
            logOutButton.visibility = View.VISIBLE

            if (titleTextView != null) {
                titleTextView.text = "Logged In"
            }
        } else {
            val titleTextView = view?.findViewById<TextView>(R.id.titleTextView)
            emailEditText.visibility = View.VISIBLE
            passwordEditText.visibility = View.VISIBLE
            signUpButton.visibility = View.VISIBLE
            logOutButton.visibility = View.GONE

            if (titleTextView != null) {
                titleTextView.text = "Sign up"
            }
        }
    }


}