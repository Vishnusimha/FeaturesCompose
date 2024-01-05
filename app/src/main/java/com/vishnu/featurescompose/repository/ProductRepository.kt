package com.vishnu.featurescompose.repository

import android.util.Log
import com.vishnu.featurescompose.data.AddProductRequest
import com.vishnu.featurescompose.data.AddProductResponse
import com.vishnu.featurescompose.data.Product
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val swipeApiService: SwipeApiService) {
    suspend fun getProducts(): List<Product>? {
        val response = swipeApiService.getProducts()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun addProduct(product: AddProductRequest): Response<AddProductResponse> {
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
