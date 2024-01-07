package com.vishnu.featurescompose.data.remote

data class AddProductResponseDto(
    val message: String,
    val product_details: ProductDetailsDto,
    val product_id: Int,
    val success: Boolean
)
