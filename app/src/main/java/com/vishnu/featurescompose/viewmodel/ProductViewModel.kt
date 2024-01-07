package com.vishnu.featurescompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnu.featurescompose.data.remote.AddProductRequestDto
import com.vishnu.featurescompose.data.remote.AddProductResponseDto
import com.vishnu.featurescompose.data.remote.ProductDto
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

    private val _products = MutableStateFlow<List<ProductDto>>(emptyList())
    val products: Flow<List<ProductDto>> get() = _products.asStateFlow()


    private val _addProductResponse = MutableStateFlow<AddProductResponseDto?>(null)
    val addProductResponse: Flow<AddProductResponseDto?> get() = _addProductResponse.asStateFlow()


//    //Full ThingSpeak cloud data
//    private val _scd40FullJsonThingSpeakCloudData = MutableStateFlow<String?>(null)
//    val scd40FullJsonThingSpeakCloudData: Flow<String?> get() = _scd40FullJsonThingSpeakCloudData.asStateFlow()

//
//    private val _products = MutableLiveData<List<ProductDto>>()
//    val products: LiveData<List<ProductDto>> get() = _products

    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getProducts()
            _products.value = result ?: emptyList()
        }
    }

    //........................................................................
    fun addProduct(product: AddProductRequestDto) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("productViewModel", product.toString())
            val response = repository.addProduct(product)
            _addProductResponse.value = response.body()

            Log.d("productViewModel", response.code().toString())
            Log.d("productViewModel", response.message())
            Log.d("productViewModel", response.body().toString())
        }
    }
}

