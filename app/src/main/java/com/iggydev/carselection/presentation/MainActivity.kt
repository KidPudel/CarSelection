package com.iggydev.carselection.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iggydev.carselection.presentation.composables.AddScreen
import com.iggydev.carselection.presentation.composables.CarsScreen
import com.iggydev.carselection.presentation.composables.DetailsScreen
import com.iggydev.carselection.ui.theme.CarSelectionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarSelectionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // i'm using basic jetpack compose navigation that is neat and simple
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Screens.CarsScreen.route
                    ) {
                        composable(route = Screens.CarsScreen.route) {
                            CarsScreen(navigationController = navigationController)
                        }
                        composable(route = Screens.DetailsScreen.route + "/{car_id}",
                            arguments = listOf(
                                navArgument(name = "car_id") {
                                    type = NavType.IntType
                                }
                            )) { backStackEntry ->
                            DetailsScreen(
                                navigationController = navigationController,
                                carId = backStackEntry.arguments?.getInt("car_id") ?: 0
                            )
                        }
                        composable(route = Screens.AddScreen.route + "/{size}",
                            arguments = listOf(
                                navArgument(name = "size") {
                                    type = NavType.IntType
                                }
                            )) { backStackEntry ->
                            AddScreen(
                                navigationController = navigationController,
                                size = backStackEntry.arguments?.getInt("size") ?: 0
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarSelectionTheme {
    }
}