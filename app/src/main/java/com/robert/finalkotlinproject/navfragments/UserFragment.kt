package com.robert.finalkotlinproject.navfragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.robert.finalkotlinproject.AppDatabase
import com.robert.finalkotlinproject.R
import com.robert.finalkotlinproject.user.User
import com.robert.finalkotlinproject.user.UserRepository
import com.robert.finalkotlinproject.user.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class UserFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var titleTextView: TextView
    private lateinit var signUpButton: Button
    private lateinit var logOutButton: Button
    private lateinit var loginButton: Button
    private lateinit var editAccount: Button
    private lateinit var deleteAccount: Button
    private lateinit var descriptionText : TextView

    private var isLoggedIn: Boolean = false
    private val viewModel: UserViewModel by viewModels()
    private var loggedInUsername: String? = null

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("MY_APP_PREFS", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        loggedInUsername = sharedPreferences.getString("LOGGED_IN_USERNAME", null)

        val db = AppDatabase.getInstance(view.context)
        val userRepository = UserRepository(db, lifecycleScope)



        emailEditText = view.findViewById(R.id.et_email)
        passwordEditText = view.findViewById(R.id.et_password)
        titleTextView = view.findViewById(R.id.titleTextView)
        loginButton = view.findViewById(R.id.btn_login)
        logOutButton = view.findViewById(R.id.btn_log_out)
        signUpButton = view.findViewById(R.id.btn_sign_up)
        editAccount = view.findViewById(R.id.btn_edit_account)
        deleteAccount = view.findViewById(R.id.btn_delete_account)
        descriptionText = view.findViewById(R.id.DescriptionTextView)


        isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false)
        updateUI()
        if (isLoggedIn) {
            emailEditText.visibility = View.GONE
            passwordEditText.visibility = View.GONE
            signUpButton.visibility = View.GONE
            loginButton.visibility = View.GONE
            logOutButton.visibility = View.VISIBLE


            titleTextView.text = "Welcome back\n${loggedInUsername ?: ""}"
        } else {
            emailEditText.visibility = View.VISIBLE
            passwordEditText.visibility = View.VISIBLE
            signUpButton.visibility = View.VISIBLE
            loginButton.visibility = View.VISIBLE
            logOutButton.visibility = View.GONE

            titleTextView.text = "Sign up"
        }

        viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {

                println("Logged in")

            } else {

                println("Not logged in")

            }
        }

        viewModel.loginUser("username", "password")
        viewModel.signUpUser("username", "password")

        signUpButton.setOnClickListener {


            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            signupUser(userRepository)

            //Navigation.findNavController (view).navigate(R.id.action_userFragment_to_homeFragment)

        }

        loginButton.setOnClickListener {
            val username = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginUser(userRepository, username, password)



        }


        // Set click listener for the log out button
        logOutButton.setOnClickListener {

            isLoggedIn = false
            viewModel.logoutUser()
            updateUI()
            //Navigation.findNavController (view).navigate(R.id.action_userFragment_to_homeFragment)
        }


        editAccount.setOnClickListener {
            val input = EditText(requireContext())
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            input.setText(loggedInUsername ?: "")
            input.gravity = Gravity.CENTER

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Edit Account")
                .setMessage("Enter your new password for $loggedInUsername:")
                .setView(input)
                .setPositiveButton("Save") { _, _ ->
                    // Call the update password function
                    val newPassword = input.text.toString()
                    if (newPassword.isNotEmpty()) {
                        runBlocking {
                            userRepository.updateUserPassword(loggedInUsername ?: "", newPassword)
                        }
                        Toast.makeText(requireContext(), "Password updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()
        }

        deleteAccount.setOnClickListener {
            // Show a dialog asking the user to confirm the deletion
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes") { dialog, which ->
                    // Call deleteUserByUsername from a coroutine
                    lifecycleScope.launch {

                            viewModel.logoutUser()
                            isLoggedIn = false
                            userRepository.deleteUserByUsername(loggedInUsername ?: "")
                            updateUI()
                        //Navigation.findNavController (view).navigate(R.id.action_userFragment_to_homeFragment)

                    }
                }
                .setNegativeButton("No", null)
                .create()
            dialog.show()
        }


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
                loginButton.visibility = View.GONE
                logOutButton.visibility = View.VISIBLE
                editAccount.visibility = View.VISIBLE
                deleteAccount.visibility = View.VISIBLE
                descriptionText.text =
                    "Thank you for being a member! As a gift you can use the discount code - 5off - at your next purchase"


                if (titleTextView != null) {
                    titleTextView.text = "Welcome\n${loggedInUsername ?: ""}"
                }

            } else {
                val titleTextView = view?.findViewById<TextView>(R.id.titleTextView)
                emailEditText.visibility = View.VISIBLE
                passwordEditText.visibility = View.VISIBLE
                signUpButton.visibility = View.VISIBLE
                loginButton.visibility = View.VISIBLE
                logOutButton.visibility = View.GONE
                editAccount.visibility = View.GONE
                deleteAccount.visibility = View.GONE
                descriptionText.text =
                    "As a member you can get exclusive deals and notifications of new products or offers. If you wish to unsubscribe from our newsletter, remove your account"

                if (titleTextView != null) {
                    titleTextView.text = "Sign up"
                }


            }

            with(sharedPreferences.edit()) {
                putBoolean("IS_LOGGED_IN", isLoggedIn)
                apply()
            }

    }

    private fun signupUser(userRepository: UserRepository) {
        val username = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Check if the email and password fields are not empty and if the email is valid
        if (username.isNotEmpty() && password.isNotEmpty() && isValidEmail(username)) {
            userRepository.performDatabaseOperation(Dispatchers.IO) {
                // Check if the user with the same username already exists in the database
                val existingUser = userRepository.getUserByUsername(username)
                if (existingUser == null) {
                    // Insert the new user into the database
                    userRepository.insertUser(User(username, password))
                    val callback: suspend (List<User>) -> Unit = { user: List<User> ->
                        handleUserList(user, username, password)
                    }
                    lifecycleScope.launch {
                        userRepository.getUsersFlow(username, password).collect(callback)
                    }
                    sharedPreferences.edit().putString("LOGGED_IN_USERNAME", username).apply()
                    isLoggedIn = true
                    loggedInUsername = username

                    // Wrap UI-related code inside runOnUiThread
                    requireActivity().runOnUiThread {
                        passwordEditText.setText("")
                        emailEditText.setText("")
                        view?.clearFocus()
                        updateUI()
                    }
                } else {
                    // If the user already exists, show an error message
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Username already taken. Please choose a different one", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            // If either the email or password field is empty or the email is invalid, show an error message
            emailEditText.error = "Invalid email address"
            Toast.makeText(requireContext(), "Please enter a valid email and password", Toast.LENGTH_SHORT).show()
        }
    }


    private fun isValidEmail(username: String): Boolean {
        val emailRegex = Regex(pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        return emailRegex.matches(username)
    }


    private fun loginUser(userRepository: UserRepository, username: String, password: String) {
        // Check if the email and password fields are not empty
        if (username.isNotEmpty() && password.isNotEmpty()) {
            // Create a callback that updates the UI based on the list of users received
            val callback: suspend (List<User>) -> Unit = { user: List<User> ->
                handleUserList(user, username, password)
            }

            // Observe the list of users using a flow and collect it in the lifecycle scope
            lifecycleScope.launch {
                userRepository.getUsersFlow(username, password).collect(callback)
            }

            sharedPreferences.edit().putString("LOGGED_IN_USERNAME", username).apply()

        } else {
            // If either the email or password field is empty, show an error message
            Toast.makeText(requireContext(), "Please enter your email and password", Toast.LENGTH_SHORT).show()
        }
    }



    private fun handleUserList(user: List<User>, username: String, password: String) {
        if (user.any { it.username == username && it.password == password }) {
            isLoggedIn = true
            loggedInUsername = username

            requireActivity().runOnUiThread {
                passwordEditText.setText("")
                emailEditText.setText("")
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                view?.clearFocus()
                updateUI()
            }
        } else {
            Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }


}
