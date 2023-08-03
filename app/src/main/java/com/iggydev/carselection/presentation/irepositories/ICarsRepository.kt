package com.iggydev.carselection.presentation.irepositories

import com.iggydev.carselection.data.database.models.CarDBModel

interface ICarsRepository {
     suspend fun getAllCars(): List<CarDBModel>

     suspend fun getCar(id: Int): CarDBModel

     suspend fun getCarsByManufacture(manufacture: String): List<CarDBModel>

     suspend fun getCarsByBrand(brand: String): List<CarDBModel>

     suspend fun addCar(newCar: CarDBModel)

     suspend fun editCar(carId: Int, newManufacture: String, newBrand: String, newModel: String)

     suspend fun sortCarsAscending(): List<CarDBModel>

     suspend fun sortCarsDescending(): List<CarDBModel>

     suspend fun removeCar(carToRemove: CarDBModel)

}