package com.vishnu.featurescompose.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vishnu.featurescompose.data.local.BeerDatabase
import com.vishnu.featurescompose.data.local.BeerEntity
import com.vishnu.featurescompose.data.mappers.toBeerEntity
import retrofit2.HttpException
import java.io.IOException

/** *BeerRemoteMediator* is like Repository alternative, because here we are implementing all BeerApi functions*/
@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDb: BeerDatabase,
    private val beerApi: BeerApi
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            /** loadKey, helps in next page to load i.e page in the link https://api.punkapi.com/v2/beers?page=4&per_page=80.
             * Handling the logic for determining the page to be loaded when the load type is LoadType.APPEND. In a paginated data source, when the user scrolls to the end of the currently loaded data, a new page is requested to append more items to the existing list.*/
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    /** It retrieves the last item from the current loaded data set (state). The lastItemOrNull() function likely returns the last item in the list, or null if the list is empty.If there is a last item, it calculates the next page by dividing the id of the last item by the page size specified in the state.config. The + 1 is added to ensure that the next page is loaded.*/
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            Log.d("BeerRemoteMediator", "Load Key: $loadKey")
            Log.d("BeerRemoteMediator", "state.config.pageSize: ${state.config.pageSize}")
//getting beers
            val beers = beerApi.getBeers(
                page = loadKey,
                pageCount = state.config.pageSize
            )

//loading beers into local database
            beerDb.withTransaction {
                //checking load type
                if (loadType == LoadType.REFRESH) {
                    Log.d("BeerRemoteMediator", "LoadType.REFRESH: $loadKey")
                    beerDb.dao.clearAll()
                }
                //Converting normal beers to beer entities i.e local database format
                val beerEntities = beers.map { it.toBeerEntity() }
                Log.d("BeerRemoteMediator", "upsertAll(beerEntities): $loadKey")
                beerDb.dao.upsertAll(beerEntities)
            }

            /** Return the MediatorResult.Success. we need to determine whether we reached end of pagination or not. How ? we check if the beers are empty*/
            MediatorResult.Success(
                endOfPaginationReached = beers.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
