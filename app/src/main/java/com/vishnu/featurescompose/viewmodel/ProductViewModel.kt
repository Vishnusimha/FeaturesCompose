package com.vishnu.featurescompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnu.featurescompose.data.AddProductRequest
import com.vishnu.featurescompose.data.AddProductResponse
import com.vishnu.featurescompose.data.Product
import com.vishnu.featurescompose.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: Flow<List<Product>> get() = _products.asStateFlow()


    private val _addProductResponse = MutableStateFlow<AddProductResponse?>(null)
    val addProductResponse: Flow<AddProductResponse?> get() = _addProductResponse.asStateFlow()


//    //Full ThingSpeak cloud data
//    private val _scd40FullJsonThingSpeakCloudData = MutableStateFlow<String?>(null)
//    val scd40FullJsonThingSpeakCloudData: Flow<String?> get() = _scd40FullJsonThingSpeakCloudData.asStateFlow()

//
//    private val _products = MutableLiveData<List<Product>>()
//    val products: LiveData<List<Product>> get() = _products

    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getProducts()
            _products.value = result ?: emptyList()
        }
    }


//........................................................................


    fun addProduct(product: AddProductRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("viewModel", product.toString())
            val response = repository.addProduct(product)
            _addProductResponse.value = response.body()

            Log.d("viewModel", response.code().toString())
            Log.d("viewModel", response.message())
            Log.d("viewModel", response.body().toString())
        }
    }
}

