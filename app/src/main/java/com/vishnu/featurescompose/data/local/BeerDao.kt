package com.vishnu.featurescompose.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/** we should always display data from the cache and not from the network directly*/
@Dao
interface BeerDao {
    @Upsert
    suspend fun upsertAll(beers: List<BeerEntity>)

    @Query("SELECT * FROM beerentity")
    fun pagingSource(): PagingSource<Int, BeerEntity>

    @Query("DELETE FROM beerentity")
    suspend fun clearAll()
}
