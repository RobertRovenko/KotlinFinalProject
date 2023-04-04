package com.robert.finalkotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.robert.finalkotlinproject.cartlogic.CartViewModel

private lateinit var cartViewModel: CartViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

    }
}