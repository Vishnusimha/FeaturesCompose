package com.vishnu.featurescompose.data.remote

data class ProductDto(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)
