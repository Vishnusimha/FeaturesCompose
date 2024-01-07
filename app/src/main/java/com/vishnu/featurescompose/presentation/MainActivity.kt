package com.vishnu.featurescompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vishnu.featurescompose.domain.Beer
import com.vishnu.featurescompose.ui.theme.FeaturesComposeTheme
import com.vishnu.featurescompose.viewmodel.BeerViewModel
import com.vishnu.featurescompose.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeaturesComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    ProductsScreen(productViewModel)
//                    Beers
                    val viewModel = hiltViewModel<BeerViewModel>()
                    val beers: LazyPagingItems<Beer> =
                        viewModel.beerPagingFlow.collectAsLazyPagingItems()
//                    Getting beers from view model and sending to BeerScreen to display
                    BeerScreen(beers = beers)
                }
            }
        }
    }
}
