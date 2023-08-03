package com.iggydev.carselection.presentation

sealed class Screens(val route: String) {
    object CarsScreen: Screens(route = "cars_screen")
    object DetailsScreen: Screens(route = "details_screen")
    object AddScreen: Screens(route = "add_screen")
}
