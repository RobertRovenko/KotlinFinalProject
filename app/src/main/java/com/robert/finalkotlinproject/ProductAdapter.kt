package com.robert.finalkotlinproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.robert.finalkotlinproject.cartlogic.Product

class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cartlayout, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount() = products.size


}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameTextView: TextView = itemView.findViewById(R.id.product_name)
    private val priceTextView: TextView = itemView.findViewById(R.id.product_price)
    private val sizeTextView: TextView = itemView.findViewById(R.id.product_size)
    private val imageView: ImageView = itemView.findViewById(R.id.product_image)

    fun bind(product: Product) {
        nameTextView.text = product.name
        priceTextView.text = "$${product.price}"
        sizeTextView.text = product.size

        Glide.with(itemView.context)
            .load(product.imageUrl)
            .into(imageView)
    }
}