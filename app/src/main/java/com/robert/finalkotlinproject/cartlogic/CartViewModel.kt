package com.robert.finalkotlinproject.cartlogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class CartViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<Product>>(mutableListOf())
    val products: LiveData<MutableList<Product>> = _products

    val totalCost: LiveData<Double> = _products.map { productList ->
        var total = 0.0
        for (product in productList) {
            total += product.price
        }
        total
    }

    fun addProductToCart(product: Product) {
        _products.value?.add(product)
        _products.postValue(_products.value)
    }
}