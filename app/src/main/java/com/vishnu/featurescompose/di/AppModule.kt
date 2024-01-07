package com.vishnu.featurescompose.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.vishnu.featurescompose.data.local.BeerDatabase
import com.vishnu.featurescompose.data.local.BeerEntity
import com.vishnu.featurescompose.data.remote.BeerApi
import com.vishnu.featurescompose.data.remote.BeerRemoteMediator
import com.vishnu.featurescompose.data.remote.ProductApiService
import com.vishnu.featurescompose.repository.ProductRepository
import com.vishnu.featurescompose.viewmodel.ProductViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val SWIPE_BASE_URL = "https://app.getswipe.in/api/public/"

    @Singleton
    @Provides
    fun provideSwipeApiService(): ProductApiService {
        return Retrofit.Builder()
            .baseUrl(SWIPE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }

    @Provides
    fun provideProductRepository(productApiService: ProductApiService): ProductRepository {
        return ProductRepository(productApiService)
    }

    @Provides
    fun provideProductViewModel(repository: ProductRepository): ProductViewModel {
        return ProductViewModel(repository)
    }

    //Beers Feature ..................................................
    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): BeerDatabase {
        return Room.databaseBuilder(
            context,
            BeerDatabase::class.java,
            "beers.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBeerApi(): BeerApi {
        return Retrofit.Builder()
            .baseUrl(BeerApi.BEERS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBeerPager(beerDb: BeerDatabase, beerApi: BeerApi): Pager<Int, BeerEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = BeerRemoteMediator(
                beerDb = beerDb,
                beerApi = beerApi
            ),
            pagingSourceFactory = {
                beerDb.dao.pagingSource()
            }
        )
    }
}
