package com.iggydev.carselection.presentation.states

import com.iggydev.carselection.data.database.models.CarDBModel

data class CarState(
    val cars: List<CarDBModel>? = null,
    val car: CarDBModel? = null
)
