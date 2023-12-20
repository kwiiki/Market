package com.example.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market.models.ProductDetails

class SharedViewModel : ViewModel() {
    private val selectedProduct = MutableLiveData<MutableList<ProductDetails>>()


    init {
        selectedProduct.value = mutableListOf()
    }

    fun selectProduct(product: ProductDetails) {
        // Create a new list and copy existing elements to avoid modifying the existing list directly
        val newList = selectedProduct.value?.toMutableList() ?: mutableListOf()
        newList.add(product)
        selectedProduct.value = newList
    }

    fun getSelectedProduct(): MutableLiveData<MutableList<ProductDetails>> {
        return selectedProduct
    }
}