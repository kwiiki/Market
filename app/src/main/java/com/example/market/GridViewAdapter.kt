package com.example.market

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.market.models.Product

class GridViewAdapter(
    private val context: Context,
    private var productList: List<Product>
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return productList.size
    }


    override fun getItem(position: Int): Any? {
        return if (position in this.productList.indices) productList[position] else null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.gridview_item, null)
        }

        // Initialize views only once
        val productImage: ImageView = convertView!!.findViewById(R.id.RV_image)
        val productTitle: TextView = convertView.findViewById(R.id.title_rv)
        val productPrice: TextView = convertView.findViewById(R.id.price_rv)

        // Load image using Glide
        Glide.with(context).load(productList[position].thumbnail).into(productImage)

        // Set text values
        productTitle.text = productList[position].title
        productPrice.text = productList[position].price.toString()

        convertView.setOnClickListener {
            val productDetailFragment = ProductDetailFragment()
            val bundle = Bundle()
            bundle.putInt("productId", productList[position].id)
            productDetailFragment.arguments = bundle

            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, productDetailFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

         return convertView
//    }
    }
}