package org.example.project.navigation

sealed class DemoDestinations(val route: String) {
    data object Home : DemoDestinations("home")
    data object Movie : DemoDestinations("movie/{id}")
}