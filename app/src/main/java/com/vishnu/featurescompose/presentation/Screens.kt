package com.vishnu.featurescompose.presentation

sealed class Screen(val route: String) {
    object LandingScreen : Screen("landing_screen")
    object ProductsScreen : Screen("products_screen")
    object BeerScreen : Screen("beer_screen")
    object FirebaseScreen : Screen("firebase_screen")
}
