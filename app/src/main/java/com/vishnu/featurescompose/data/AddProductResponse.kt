package com.vishnu.featurescompose.data

data class AddProductResponse(
    val message: String,
    val product_details: ProductDetails,
    val product_id: Int,
    val success: Boolean
)
