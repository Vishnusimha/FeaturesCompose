package com.vishnu.featurescompose.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vishnu.featurescompose.data.remote.AddProductRequestDto
import com.vishnu.featurescompose.data.remote.ProductDto
import com.vishnu.featurescompose.ui.theme.Purple80
import com.vishnu.featurescompose.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(viewModel: ProductViewModel) {

    val products by viewModel.products.collectAsState(emptyList())
    val addProductResponse by viewModel.addProductResponse.collectAsState(null)

//    Scaffold related
    val scaffoldState = rememberScaffoldState()
    var presses by remember { mutableIntStateOf(0) }


//TODO This code is like init for Composable
    LaunchedEffect(key1 = viewModel) {
//        viewModel.fetchProducts()
        Log.d("ProductsScreen", "LaunchedEffect")

        viewModel.addProduct(
            AddProductRequestDto(
                product_name = "Ice Cream",
                product_type = "Food",
                price = "100.0",
                tax = "5.0"
            )
        )
    }

    @Composable
    fun ProductRow(product: ProductDto, color: Color) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color)
                    .padding(8.dp)
            ) {
                Text(text = "ProductDto Name: ${product.product_name}")
                Text(text = "ProductDto Type: ${product.product_type}")
                Text(text = "Selling Price: ${product.price}")
                Text(text = "Tax: ${product.tax}")

                // Check if image is not empty before displaying
                if (product.image.isNotBlank()) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = null,
                    )
                }
            }
        }
    }


//    Surface(
//        elevation = 2.dp,
//        color = Color.White,
//        shape = androidx.compose.material.MaterialTheme.shapes.small
//    ) {
//
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()@
//                .padding(2.dp)
//        ) {
//            Text(
//                text = "Products",
//                color = Color.Gray,
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    fontSize = 30.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            )
//
//            Button(modifier = Modifier
//                .padding(8.dp)
//                .align(Alignment.CenterHorizontally)
//                .wrapContentSize(),
//                onClick = {
////                    viewModel.fetchProducts()
//                    viewModel.addProduct(
//                        AddProductRequestDto(
//                            product_name = "Ice Cream",
//                            product_type = "Food",
//                            price = "100.0",
//                            tax = "5.0"
//                        )
//                    )
//                }) {
//                Text(text = "fetchProducts")
//            }
//
//            Column(modifier = Modifier.padding(0.dp)) {
//                LazyColumn {
//                    items(products) { product ->
//                        ProductRow(product = product, Purple80)
//                    }
//                }
//            }
//
//
//            // Display the result of the post call
//            addProductResponse?.let { response ->
//                if (response.success) {
//                    Snackbar(
//                        modifier = Modifier.padding(16.dp),
//                        action = {
//                            // Optional: You can add an action button to the Snackbar
//                            TextButton(onClick = {
//                                // Handle action button click if needed
//                                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
//                            }) {
//                                Text("Dismiss")
//                            }
//                        }
//                    ) {
//                        Text("ProductDto added successfully: ${response.product_details.product_name}")
//                    }
//                } else {
//                    Snackbar(
//                        modifier = Modifier.padding(16.dp),
//                        action = {
//                            // Optional: You can add an action button to the Snackbar
//                            TextButton(onClick = {
//                                // Handle action button click if needed
//                            }) {
//                                Text("Dismiss")
//                            }
//                        }
//                    ) {
//                        Text("Error adding product: ${response.message}", color = Color.Red)
//                    }
//                }
//            }
//        }
//    }
//

//... main screen code
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
// My code inside scaffold documentation code
        Surface(
            modifier = Modifier
                .padding(innerPadding),
            elevation = 2.dp,
            color = Color.White,
            shape = androidx.compose.material.MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
            ) {
                Text(
                    text = "Products",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Button(modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .wrapContentSize(),
                    onClick = {
                        viewModel.fetchProducts()
                        viewModel.addProduct(
                            AddProductRequestDto(
                                product_name = "Ice Cream",
                                product_type = "Food",
                                price = "100.0",
                                tax = "5.0"
                            )
                        )
                    }) {
                    Text(text = "fetchProducts")
                }

                Column(modifier = Modifier.padding(0.dp)) {
                    LazyColumn {
                        items(products) { product ->
                            ProductRow(product = product, Purple80)
                        }
                    }
                }

                // Display the result of the post call
                addProductResponse?.let { response ->
                    if (response.success) {
                        Snackbar(
                            modifier = Modifier.padding(16.dp),
                            action = {
                                // Optional: You can add an action button to the Snackbar
                                TextButton(onClick = {
                                    // Handle action button click if needed
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                }) {
                                    Text("Dismiss")
                                }
                            }
                        ) {
                            Text("ProductDto added successfully: ${response.product_details.product_name}")
                        }
                    } else {
                        Snackbar(
                            modifier = Modifier.padding(16.dp),
                            action = {
                                // Optional: You can add an action button to the Snackbar
                                TextButton(onClick = {
                                    // Handle action button click if needed
                                }) {
                                    Text("Dismiss")
                                }
                            }
                        ) {
                            Text("Error adding product: ${response.message}", color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}


