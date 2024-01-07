package com.vishnu.featurescompose.data.mappers

import com.vishnu.featurescompose.data.local.BeerEntity
import com.vishnu.featurescompose.data.remote.BeerDto
import com.vishnu.featurescompose.domain.Beer

fun BeerDto.toBeerEntity(): BeerEntity {
    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}

fun BeerEntity.toBeer(): Beer {
    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = firstBrewed,
        imageUrl = imageUrl
    )
}
