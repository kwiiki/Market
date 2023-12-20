package com.example.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market.models.ProductDetails


class BasketFragment : Fragment() {

    private lateinit var basketItems: MutableList<ProductDetails>
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var totalPrice: TextView  // Initialize the TextView
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var totalPrices: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        val basketRecyclerView: RecyclerView = view.findViewById(R.id.basketRecyclerView)

        basketItems = mutableListOf()
        basketAdapter = BasketAdapter(requireContext(), basketItems)
        basketRecyclerView.adapter = basketAdapter
        basketRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        totalPrice = view.findViewById(R.id.totalCostTextView)  // Initialize totalPrice

        sharedViewModel.getSelectedProduct().observe(viewLifecycleOwner) { products ->
            updateBasket(products)
        }

        return view
    }

    private fun updateBasket(products: List<ProductDetails>) {
        basketItems.clear()
        basketItems.addAll(products)
        calculateTotalPrices()
        basketAdapter.notifyDataSetChanged()
    }

    private fun calculateTotalPrices() {
        totalPrices = basketItems.sumOf { it.price }
        totalPrice.text = totalPrices.toString()
        // Update UI or perform any other action with the totalPrices value
    }
}
