package com.iggydev.carselection.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iggydev.carselection.presentation.Screens
import com.iggydev.carselection.presentation.states.UIState
import com.iggydev.carselection.presentation.viewmodels.CarsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsScreen(navigationController: NavHostController) {
    val carsViewModel = hiltViewModel<CarsViewModel>()
    val carsCoroutineScope = rememberCoroutineScope()
    var isLoading by remember {
        mutableStateOf(true)
    }
    var choosingManufacture by remember {
        mutableStateOf(false)
    }
    var choosingBrand by remember {
        mutableStateOf(false)
    }
    var sorting by remember {
        mutableStateOf(false)
    }

    val horizontalScroll = rememberScrollState()

    LaunchedEffect(key1 = true) {
        runBlocking {
            carsViewModel.getCars()
        }
        carsCoroutineScope.launch {
            carsViewModel.uiState.collectLatest { state ->
                when (state) {
                    is UIState.Loading -> {
                        isLoading = true
                    }

                    is UIState.Loaded -> {
                        isLoading = false
                    }

                    else -> {
                        isLoading = true
                    }
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                text = "Car Selection",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        })
    }) {
        val columnLazyState = rememberLazyListState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (isLoading) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Loading...", fontSize = 30.sp)
                Spacer(modifier = Modifier.weight(1f))

            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                }
                if (sorting || choosingBrand || choosingManufacture) {
                    Button(
                        onClick = {
                            runBlocking {
                                carsViewModel.getCars()
                            }
                            sorting = false
                            choosingBrand = false
                            choosingManufacture = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(36.dp),
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(width = 2.dp, color = Color.Black),

                        ) {
                        Text(text = "Back")
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(state = horizontalScroll, enabled = true),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (!sorting && !choosingBrand && !choosingManufacture) {
                            Button(
                                onClick = {
                                    navigationController.navigate(route = Screens.AddScreen.route + "/${carsViewModel.state.value.cars!!.size}")
                                },
                                modifier = Modifier
                                    .height(36.dp),
                                shape = RoundedCornerShape(15.dp),
                                border = BorderStroke(width = 2.dp, color = Color.Black),

                                ) {
                                Text(text = "Add new car")
                            }
                        }
                        Button(
                            onClick = {
                                runBlocking {
                                    choosingManufacture = true
                                    //carsViewModel.getCarsByManufacture()
                                }
                            },
                            modifier = Modifier
                                .height(36.dp),
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(width = 2.dp, color = Color.Black),

                            ) {
                            Text(text = "get cars by manufacture")
                        }
                        Button(
                            onClick = {
                                runBlocking {
                                    choosingBrand = true
                                }
                            },
                            modifier = Modifier
                                .height(36.dp),
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(width = 2.dp, color = Color.Black),

                            ) {
                            Text(text = "get cars by brand")
                        }

                        Button(
                            onClick = {
                                runBlocking {
                                    sorting = true
                                }
                            },
                            modifier = Modifier
                                .height(36.dp),
                            shape = RoundedCornerShape(15.dp),
                            border = BorderStroke(width = 2.dp, color = Color.Black),

                            ) {
                            Text(text = "sort by price")
                        }
                    }

                }
                if (sorting) {
                    Button(
                        onClick = {
                            runBlocking {
                                carsViewModel.sortCarsAscending()
                                sorting = false
                            }
                        },
                        border = BorderStroke(width = 2.dp, color = Color.Black),
                    ) {
                        Text(text = "cheapest first")
                    }
                    Button(
                        onClick = {
                            runBlocking {
                                carsViewModel.sortCarsDescending()
                                sorting = false
                            }
                        },
                        border = BorderStroke(width = 2.dp, color = Color.Black),
                    ) {
                        Text(text = "expensive first")
                    }
                } else if (choosingBrand) {
                    LazyColumn(state = columnLazyState, userScrollEnabled = true) {
                        items(items = carsViewModel.state.value.cars?.map { it.brand }
                            ?.distinct()!!) { car ->
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(width = 2.dp, color = Color.Black),
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth(0.9f)
                                    .clickable {
                                        runBlocking {
                                            carsViewModel.getCarsByBrand(brand = car ?: "")
                                            choosingBrand = true
                                        }
                                    }) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = car ?: "", color = Color.Black)

                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))

                        }
                    }
                } else if (choosingManufacture) {
                    LazyColumn(state = columnLazyState, userScrollEnabled = true) {
                        items(items = carsViewModel.state.value.cars?.map { it.manufacture }
                            ?.distinct()!!) { car ->
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(width = 2.dp, color = Color.Black),
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth(0.9f)
                                    .clickable {
                                        runBlocking {
                                            carsViewModel.getCarsByManufacture(
                                                manufacture = car ?: ""
                                            )
                                            choosingBrand = true

                                        }
                                    }) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = car ?: "", color = Color.Black)

                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                } else {
                    LazyColumn(state = columnLazyState, userScrollEnabled = true) {
                        items(items = carsViewModel.state.value.cars!!) { car ->
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(width = 2.dp, color = Color.Black),
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth(0.9f)
                                    .clickable { navigationController.navigate(route = Screens.DetailsScreen.route + "/${car.id}") }) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = car.brand ?: "", color = Color.Black)
                                    Text(text = "${car.price}$", color = Color.Black)
                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                }

            }

        }

    }
}
