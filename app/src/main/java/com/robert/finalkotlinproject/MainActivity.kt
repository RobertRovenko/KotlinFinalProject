package com.robert.finalkotlinproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.robert.finalkotlinproject.cartlogic.CartViewModel
import androidx.appcompat.app.AppCompatDelegate

private lateinit var cartViewModel: CartViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

    }


}