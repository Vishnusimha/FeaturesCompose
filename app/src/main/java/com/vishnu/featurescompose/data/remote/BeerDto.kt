package com.vishnu.featurescompose.data.remote

/**BeerDto data transfer object over the internet so the fields are as same as in the API JSON format fields*/
data class BeerDto(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val first_brewed: String,
    val image_url: String?
)

