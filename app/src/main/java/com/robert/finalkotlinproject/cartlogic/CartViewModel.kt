package com.robert.finalkotlinproject.cartlogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.robert.finalkotlinproject.ProductAdapter

class CartViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<Product>>(mutableListOf())
    val products: LiveData<MutableList<Product>> = _products


    // Create a new LiveData object for the discounted total cost
    private val _discountedTotalCost = MutableLiveData<Double>()
    val discountedTotalCost: LiveData<Double> = _discountedTotalCost


    // Initialize the cart total with a value of 0.0
    private val _cartTotal = MutableLiveData<Double>(0.0)
    val cartTotal: LiveData<Double> = _cartTotal

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

    fun resetDiscount() {
        discountCode = null
        discountUsed = false

        val newDiscountedTotalCost = calculateDiscountedTotalCost()
        _discountedTotalCost.postValue(newDiscountedTotalCost)
    }

    private fun calculateDiscountedTotalCost(): Double {
        val totalCost = totalCost.value ?: 0.0

        // Apply discount if discount code is valid and has not been used
        if (discountCode == "5off" && !discountUsed) {
            return totalCost * 0.95
        }

        // Return the original total cost if no discount code is applied or if discount has already been used
        return totalCost
    }

    fun addProductToCart(product: Product) {
        _products.value?.add(product)
        _products.postValue(_products.value)

    }

    fun removeProductFromCart(product: Product) {
        _products.value?.remove(product)
        _products.postValue(_products.value)
        resetDiscount()
        discountUsed = false // Reset discountUsed flag when an item is removed from the cart
    }

    fun emptyCart() {
        _products.value?.clear()
        _products.postValue(_products.value)
        resetDiscount()
        discountUsed = false // Reset discountUsed flag when the cart is emptied
    }

}