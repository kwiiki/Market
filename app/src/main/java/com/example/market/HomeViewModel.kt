package com.example.market.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market.ApiInterface
import com.example.market.models.ApiResponse
import com.example.market.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://dummyjson.com"

class HomeViewModel : ViewModel() {


    private val apiInterface: ApiInterface = createApiInterface()
    val productList: MutableLiveData<List<Product>> = MutableLiveData()

    init {
        getAllData()
    }

    private fun createApiInterface(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }

    private fun getAllData() {
        val retroData = apiInterface.getData()

        retroData.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val apiResponse = response.body()
                val data = apiResponse?.products ?: emptyList()
                productList.value = data
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    companion object {
        const val PRODUCT_ID_KEY = "productId"
    }
}
