package com.robert.finalkotlinproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.robert.finalkotlinproject.cartlogic.Cart
import com.robert.finalkotlinproject.cartlogic.CartViewModel
import com.robert.finalkotlinproject.cartlogic.Product

class ProductAdapter(
    var products: List<Product>,
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cartlayout, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product, cartViewModel)
    }

    override fun getItemCount() = products.size
}

class ProductViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val nameTextView: TextView = itemView.findViewById(R.id.product_name)
    private val priceTextView: TextView = itemView.findViewById(R.id.product_price)
    private val sizeTextView: TextView = itemView.findViewById(R.id.product_size)
    private val imageView: ImageView = itemView.findViewById(R.id.product_image)
    private val removeImageButton: ImageButton = itemView.findViewById(R.id.removeItemImageButton)
    private var product: Product? = null

    fun bind(product: Product, cartViewModel: CartViewModel) {
        this.product = product
        nameTextView.text = product.name
        priceTextView.text = "$${product.price}"
        sizeTextView.text = product.size

        Glide.with(itemView.context)
            .load(product.imageUrl)
            .into(imageView)

        removeImageButton.setOnClickListener {
            // Remove the item from the cart
            cartViewModel.removeProductFromCart(product)
        }

    }
}