package com.robert.finalkotlinproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val dataList: List<MyData>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var onItemClickListenerList: MutableList<((MyData) -> Unit)?> = MutableList(dataList.size) { null }

    fun setOnItemClickListener(position: Int, listener: ((MyData) -> Unit)?) {
        onItemClickListenerList[position] = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemlayout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.textView.text = data.text
        holder.textView2.text = data.subtext

        Glide.with(holder.itemView)
            .load(data.imageResourceId)
            .placeholder(R.drawable.cart)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClickListenerList[position]?.invoke(data)
            println("click")
        }
    }




}