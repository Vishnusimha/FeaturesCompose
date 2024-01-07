package com.vishnu.featurescompose.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/** https://api.punkapi.com/v2/beers?page=1&per_page=20
 *
 * There are total 325 Beers data in this API, we get them and show using pagination*/
interface BeerApi {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): List<BeerDto>

    companion object {
        const val BEERS_BASE_URL = "https://api.punkapi.com/v2/"
    }
}
