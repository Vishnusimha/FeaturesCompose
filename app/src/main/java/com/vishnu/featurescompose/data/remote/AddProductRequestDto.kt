package com.vishnu.featurescompose.data.remote

data class AddProductRequestDto(
    val product_name: String,
    val product_type: String,
    val price: String,
    val tax: String
)
