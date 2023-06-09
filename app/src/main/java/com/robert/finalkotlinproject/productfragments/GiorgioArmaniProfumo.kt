package com.robert.finalkotlinproject.productfragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.robert.finalkotlinproject.R
import com.robert.finalkotlinproject.cartlogic.Cart
import com.robert.finalkotlinproject.cartlogic.CartViewModel
import com.robert.finalkotlinproject.cartlogic.Product


class GiorgioArmaniProfumo : Fragment() {

    private val viewModel: CartViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_giorgio_armani_profumo, container, false)
        val goToCart : Button = view.findViewById(R.id.add_to_cart_button)
        val goBack : ImageButton = view.findViewById(R.id.btn_return)
        val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fadein)
        val addedToCartImage = view.findViewById<ImageView>(R.id.addedtocartimage)

        val product = Product("Gio Armani Profumo", 80.0, "50 ml", R.drawable.gioprofumo)
        view.findViewById<ImageView>(R.id.product_image).setImageResource(product.imageUrl)

        addedToCartImage.visibility = View.GONE

        goToCart.setOnClickListener(){

            viewModel.addProductToCart(product)
            Cart.products.add(product)

            Toast.makeText(requireContext(), "Added Giorgio Armani Profumo", Toast.LENGTH_SHORT).show()
            addedToCartImage.visibility = View.VISIBLE
            addedToCartImage.startAnimation(fadeInAnimation)

            fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val fadeOutAnimation = AlphaAnimation(1.0f, 0.0f)
                        fadeOutAnimation.duration = 500
                        fadeOutAnimation.fillAfter = true
                        addedToCartImage.startAnimation(fadeOutAnimation)

                        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation) {}

                            override fun onAnimationEnd(animation: Animation) {
                                addedToCartImage.visibility = View.GONE
                            }

                            override fun onAnimationRepeat(animation: Animation) {}
                        })
                    }, 1000)
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }

        goBack.setOnClickListener(){

            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_giorgioArmaniProfumo_to_exploreFragment)
        }
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle home click
                    Navigation.findNavController (view).navigate(R.id.action_giorgioArmaniProfumo_to_homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    // Handle search click

                    Navigation.findNavController (view).navigate(R.id.action_giorgioArmaniProfumo_to_exploreFragment)

                    true
                }
                R.id.navigation_cart -> {
                    // Handle cart click
                    Navigation.findNavController (view).navigate(R.id.action_giorgioArmaniProfumo_to_cartFragment)
                    true
                }
                R.id.navigation_user -> {
                    // Handle user click
                    Navigation.findNavController (view).navigate(R.id.action_giorgioArmaniProfumo_to_userFragment)
                    true
                }
                else -> false
            }
        }

        return view
    }
}



