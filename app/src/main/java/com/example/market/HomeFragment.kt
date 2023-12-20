package com.example.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.market.models.ApiResponse
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://dummyjson.com"

class HomeFragment : Fragment() {
    lateinit var courseGRV: GridView
    lateinit var myAdapter: GridViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
//        val cache = Cache(requireContext().cacheDir, cacheSize)
//
//        val okHttpClient = OkHttpClient.Builder()
//            .cache(cache)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()


        val view = inflater.inflate(R.layout.fragment_home, container, false)
        courseGRV = view.findViewById(R.id.idGRV)
        courseGRV.setOnItemClickListener { _, _, position, _ ->
            val productId = myAdapter.getItemId(position).toInt()

            // Создаем новый фрагмент для отображения информации о продукте
            val productDetailFragment = ProductDetailFragment()

            // Передаем id продукта через Bundle
            val args = Bundle()
            args.putInt(PRODUCT_ID_KEY, productId)
            productDetailFragment.arguments = args

            // Заменяем текущий фрагмент на новый
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, productDetailFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        getAllData()
        return view
    }

    private fun getAllData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val retroData = apiInterface.getData()

        retroData.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val apiResponse = response.body()
                val data = apiResponse?.products ?: emptyList()

                myAdapter = GridViewAdapter(requireContext(), data)
                courseGRV.adapter = myAdapter

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