package com.vishnu.featurescompose.repository

import android.util.Log
import com.vishnu.featurescompose.data.remote.AddProductRequestDto
import com.vishnu.featurescompose.data.remote.AddProductResponseDto
import com.vishnu.featurescompose.data.remote.ProductDto
import com.vishnu.featurescompose.data.remote.SwipeApiService
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val swipeApiService: SwipeApiService) {
    suspend fun getProducts(): List<ProductDto>? {
        val response = swipeApiService.getProducts()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun addProduct(product: AddProductRequestDto): Response<AddProductResponseDto> {
        return try {
            Log.d("ProductRepository", "addProduct called")
            swipeApiService.addProduct(
                product.product_name,
                product.product_type,
                product.price,
                product.tax
            )
        } catch (e: Exception) {
            // Handle exception
            Log.d("ProductRepository", "Exception adding product: $e")
            // You might want to return a default response or handle it based on your app's logic
            Response.error(500, okhttp3.ResponseBody.create(null, ""))
        }
    }

}
