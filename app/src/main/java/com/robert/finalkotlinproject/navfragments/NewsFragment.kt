package com.robert.finalkotlinproject.navfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.robert.finalkotlinproject.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class NewsFragment : Fragment() {

    private lateinit var emailEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        // Initialize views
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        emailEditText = view.findViewById(R.id.email_edit_text)
        val submitButton = view.findViewById<Button>(R.id.submit_button)
        val goBack : ImageButton = view.findViewById(R.id.btn_return)


        goBack.setOnClickListener(){

            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_newsFragment_to_homeFragment)
        }

        // Set listener for submit button
        submitButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()


            if (email.isNotEmpty() && isValidEmail(email)) {

                showSnackbar(view, email)

            } else {

                emailEditText.text.clear()
                emailEditText.error = "Invalid email address"

            }

            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            // Remove focus from EditText
            emailEditText.clearFocus()

        }

        // Set listener for BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle home click
                    Navigation.findNavController(view).navigate(R.id.action_newsFragment_to_homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    // Handle search click
                    Navigation.findNavController(view).navigate(R.id.action_newsFragment_to_exploreFragment)
                    true
                }
                R.id.navigation_cart -> {
                    // Handle cart click
                    Navigation.findNavController(view).navigate(R.id.action_newsFragment_to_cartFragment)
                    true
                }
                R.id.navigation_user -> {
                    // Handle user click
                    Navigation.findNavController(view).navigate(R.id.action_newsFragment_to_userFragment)
                    true
                }
                else -> false
            }
        }


        bottomNavigationView?.selectedItemId = R.id.newsFragment

        return view
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex(pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        return emailRegex.matches(email)
    }


    private fun showSnackbar(view: View, email: String) {
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
                message.subject = "Subscription confirmation"
                message.setText("Thank you for subscribing to our newsletter!\n" +
                        "Get 5 % off your next order with the code - 5off - \n\n\n" +
                        "Fragrance Shop")
                Transport.send(message)

                withContext(Dispatchers.Main) {
                    val snackbar = Snackbar.make(
                        view,
                        "Thank you for subscribing" +
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
