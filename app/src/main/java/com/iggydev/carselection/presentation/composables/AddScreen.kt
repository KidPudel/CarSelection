package com.iggydev.carselection.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iggydev.carselection.R
import com.iggydev.carselection.presentation.Screens
import com.iggydev.carselection.presentation.states.UIState
import com.iggydev.carselection.presentation.viewmodels.CarsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navigationController: NavHostController, size: Int) {
    val carsViewModel = hiltViewModel<CarsViewModel>()
    val detailCoroutineScope = rememberCoroutineScope()
    var isLoading by remember {
        mutableStateOf(true)
    }

    val manufacture = remember { mutableStateOf("") }
    val brand = remember { mutableStateOf("") }
    val model = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }


    LaunchedEffect(key1 = true) {
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
        TopAppBar(title = { Text(text = "Add Car") },
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add new car",
                fontWeight = FontWeight.SemiBold,
                fontSize = 56.sp,
            )
            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                state = manufacture,
                placeholder = "manufacturer",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(29.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(60.dp))
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(60.dp))
            )
            Spacer(modifier = Modifier.height(5.dp))
            CustomTextField(
                state = brand,
                placeholder = "brand",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(29.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(60.dp))
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(60.dp))

            )
            Spacer(modifier = Modifier.height(5.dp))

            CustomTextField(
                state = model,
                placeholder = "model",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(29.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(35.dp))
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(60.dp))

            )
            Spacer(modifier = Modifier.height(5.dp))

            CustomTextField(
                state = price,
                placeholder = "price",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(29.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(35.dp))
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(60.dp))

            )
            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = {
                    runBlocking {
                        carsViewModel.addCar(
                            manufacture = manufacture.value,
                            brand = brand.value,
                            model = model.value,
                            price = price.value.toIntOrNull() ?: 0,
                            size = size
                        )
                    }
                }, modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(46.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(width = 2.dp, color = Color.Black),

            ) {
                Text(text = "Add")
            }
        }

    }
}