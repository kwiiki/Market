package com.example.market

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market.models.ProductDetails
class BasketAdapter(private val context: Context, private val basketItems: MutableList<ProductDetails>) :
    RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct: ProductDetails = basketItems[position]

        Glide.with(context).load(currentProduct.thumbnail).into(holder.productImage)
        holder.productTitle.text = currentProduct.title
        holder.productPrice.text = currentProduct.price.toString()
    }

    override fun getItemCount(): Int {
        return basketItems.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.RV_image)
        val productTitle: TextView = view.findViewById(R.id.title_rv)
        val productPrice: TextView = view.findViewById(R.id.price_rv)
    }
}
