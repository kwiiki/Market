package com.example.market

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.market.models.ProductDetails
import com.example.market.viewmodel.HomeViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://dummyjson.com"
class ProductDetailFragment : Fragment() {
    private lateinit var productTitleTextView: TextView
    private lateinit var productDescriptionTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productImage: ImageView
    private lateinit var productButton: Button
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var productId: Int = -1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        arguments?.let {
            productId = it.getInt(HomeViewModel.PRODUCT_ID_KEY, -1)
        }
        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)
        productTitleTextView = view.findViewById(R.id.productTitleTextView)
        productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView)
        productPriceTextView = view.findViewById(R.id.productPriceTextView)
        productImage = view.findViewById(R.id.pdImageView)
        productButton = view.findViewById(R.id.detailButton)

        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        val cache = Cache(requireContext().cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        fetchProductDetails(retrofit, productId)
        Log.d("AAA", "Attempting to load image from: ${productId}")
        return view
    }

    private fun fetchProductDetails(retrofit: Retrofit, productId: Int) {

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val productDetailsCall = apiInterface.getProductDetail(productId)

        productDetailsCall.enqueue(object : Callback<ProductDetails> {
            override fun onResponse(call: Call<ProductDetails>, response: Response<ProductDetails>) {
                if (response.isSuccessful) {
                    val productDetails = response.body()
                    updateUI(productDetails)
                } else {
                    // Handle unsuccessful response
                    Log.e("ProductDetailsActivity", "Failed to fetch product details")
                }
            }

            override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                // Handle network failure
                Log.e("ProductDetailsActivity", "Network request failed", t)
            }
        })
    }

    private fun updateUI(productDetails: ProductDetails?) {
        Log.d("A", "Attempting to load image from: ${productDetails.toString()}")
        // Update UI with product details
        if (productDetails != null) {
            Glide.with(this).load(productDetails.thumbnail).into(productImage)
            productTitleTextView.text = productDetails.title
            val priceText = productDetails.price.toString() + "$"
            productPriceTextView.text = priceText
            productDescriptionTextView.text = productDetails.description

            // Add Buy button click listener
            productButton.setOnClickListener {
                val basketItem = ProductDetails(
                    productDetails.thumbnail,
                    productDetails.title,
                    productDetails.price,
                    productDetails.description
                )

                sharedViewModel.selectProduct(basketItem)
            }

            Log.d("AAAAAAA", "Attempting to load image from: ${productDetails.thumbnail} ${productDetails.title} ${productDetails.price}")
        } else {
            // Handle case where productDetails is null
            Log.e("ProductDetailsActivity", "Product details are null")
        }
    }


}