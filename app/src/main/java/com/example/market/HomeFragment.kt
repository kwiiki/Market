package com.example.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.market.GridViewAdapter
import com.example.market.R
import com.example.market.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var courseGRV: GridView
    private lateinit var myAdapter: GridViewAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        courseGRV = view.findViewById(R.id.idGRV)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        viewModel.productList.observe(viewLifecycleOwner, { productList ->
            myAdapter = GridViewAdapter(requireContext(), productList)
            courseGRV.adapter = myAdapter
        })
    }
}
