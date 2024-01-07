package com.vishnu.featurescompose.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.vishnu.featurescompose.domain.Beer
import kotlinx.coroutines.delay

@Composable
fun BeerScreen(
    beers: LazyPagingItems<Beer>
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = beers.loadState) {
        // when launched if there is error we show toast
        if (beers.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (beers.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (beers.loadState.refresh is LoadState.Loading) {
//            showing progress when loading
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                displaying beers data in a lazy column
                items(beers.itemCount) { index ->
                    val beer = beers[index]
                    if (beer != null) {
                        var isLoading by remember {
                            mutableStateOf(true)
                        }
                        LaunchedEffect(key1 = true) {
                            delay(5000)
                            isLoading = false
                        }

                        BeerItem(
                            beer = beer,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                item {
                    if (beers.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
