package com.iggydev.carselection.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iggydev.carselection.R
import com.iggydev.carselection.presentation.states.UIState
import com.iggydev.carselection.presentation.viewmodels.CarsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navigationController: NavHostController, carId: Int) {
    val carsViewModel = hiltViewModel<CarsViewModel>()
    val detailCoroutineScope = rememberCoroutineScope()
    var isLoading by remember {
        mutableStateOf(true)
    }


    LaunchedEffect(key1 = true) {
        runBlocking {
            carsViewModel.getCar(id = carId)
        }
        detailCoroutineScope.launch {
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
        TopAppBar(title = { Text(text = "Car Details") },
            navigationIcon = {
                IconButton(onClick = { navigationController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_navigation_back),
                        contentDescription = "navigation back icon"
                    )

                }
            })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "model: ${carsViewModel.state.value.car?.model ?: ""}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 46.sp,
            )
            Text(
                text = "brand: ${carsViewModel.state.value.car?.brand ?: ""}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
            )
            Text(
                text = "manufacture: ${carsViewModel.state.value.car?.manufacture ?: ""}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
            )
            Text(
                text = "price: ${carsViewModel.state.value.car?.price}$",
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
            )
        }

    }
}