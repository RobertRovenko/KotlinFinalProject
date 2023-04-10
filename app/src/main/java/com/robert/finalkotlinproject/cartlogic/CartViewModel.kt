package com.robert.finalkotlinproject.cartlogic

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class CartViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<Product>>(mutableListOf())
    val products: LiveData<MutableList<Product>> = _products

    // Create a new LiveData object for the discounted total cost
    private val _discountedTotalCost = MutableLiveData<Double>()
    val discountedTotalCost: LiveData<Double> = _discountedTotalCost

    val totalCost: LiveData<Double> = _products.map { productList ->
        var total = 0.0
        for (product in productList) {
            total += product.price
        }

        // Check if discount code is valid and apply discount if applicable
        if (discountCode == "5off" && !discountUsed) {
            total *= 0.95
        }

        // Update the discounted total cost LiveData object
        _discountedTotalCost.postValue(total)

        total
    }

    var discountCode: String? = null
        private set

    var discountUsed: Boolean = false

    fun setDiscountCode(code: String?) {
        discountCode = code
        discountUsed = false
    }

    fun applyDiscount() {
        var total = 0.0
        for (product in products.value ?: emptyList()) {
            total += product.price
        }

        // Check if discount code is valid and apply discount if applicable
        if (discountCode == "5off" && !discountUsed) {
            total *= 0.95
            discountUsed = true
        }

        // Update the discounted total cost LiveData object
        _discountedTotalCost.postValue(total)
    }

    fun addProductToCart(product: Product) {
        _products.value?.add(product)
        _products.postValue(_products.value)
    }

    fun resetDiscount() {
        discountCode = null
        discountUsed = false
        //Toast.makeText(getApplication(), "Discount code has been reset.", Toast.LENGTH_SHORT).show()
    }
}