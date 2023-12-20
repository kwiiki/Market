package com.example.market

import com.example.market.models.ApiResponse
import com.example.market.models.ProductDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("products?skip=0&limit=100")
    fun getData(): Call<ApiResponse>
    @GET("products/{id}")
    fun getProductDetail(@Path("id") productId: Int): Call<ProductDetails>
}