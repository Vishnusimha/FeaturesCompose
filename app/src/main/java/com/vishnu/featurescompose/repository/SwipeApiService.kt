package com.vishnu.featurescompose.repository

import com.vishnu.featurescompose.data.AddProductResponse
import com.vishnu.featurescompose.data.Product
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SwipeApiService {
    @GET("get")
    suspend fun getProducts(): Response<List<Product>>

    //    @POST("public/add")
//    suspend fun addProduct(@Body request: AddProductRequest): Response<AddProductResponse>
//
    @FormUrlEncoded
    @POST("add")
    suspend fun addProduct(
        @Field("product_name") product_name: String,
        @Field("product_type") product_type: String,
        @Field("price") price: String,
        @Field("tax") tax: String
    ): Response<AddProductResponse>

}