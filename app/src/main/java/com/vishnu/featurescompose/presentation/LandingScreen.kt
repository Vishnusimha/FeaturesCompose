package com.vishnu.featurescompose.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LandingScreen(navController: NavController) {
    Box() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Column() {
                Text(
                    text = "Features Application",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2
                )
            }
            Text(
                text = "Landing Page",
                color = Color.Black,
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(42.dp))

            Button(onClick = { navController.navigate("products_screen") }) {
                Text("products screen")
            }

            Spacer(modifier = Modifier.height(42.dp))

            Button(onClick = { navController.navigate("beer_screen") }) {
                Text("beer screen")
            }

            Spacer(modifier = Modifier.height(42.dp))

            Button(onClick = { navController.navigate("firebase_screen") }) {
                Text("firebase screen")
            }
        }
    }
}
