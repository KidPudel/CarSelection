package com.iggydev.carselection.data.repositories

import com.iggydev.carselection.data.database.CarsDatabase
import com.iggydev.carselection.data.database.models.CarDBModel
import com.iggydev.carselection.presentation.irepositories.ICarsRepository
import java.lang.reflect.Constructor
import javax.inject.Inject

class CarsRepository @Inject constructor(private val database: CarsDatabase) : ICarsRepository {
    override suspend fun getAllCars(): List<CarDBModel> {
        return database.carDao().getAllCars()
    }

    override suspend fun getCar(id: Int): CarDBModel {
        return database.carDao().getCar(id)
    }

    override suspend fun getCarsByManufacture(manufacture: String): List<CarDBModel> {
        return database.carDao().getCarsByManufacture(manufacture)
    }

    override suspend fun getCarsByBrand(brand: String): List<CarDBModel> {
        return database.carDao().getCarsByBrand(brand)
    }

    override suspend fun addCar(newCar: CarDBModel) {
        database.carDao().addCar(newCar)
    }

    override suspend fun editCar(
        carId: Int,
        newManufacture: String,
        newBrand: String,
        newModel: String
    ) {
        database.carDao().editCar(carId, newManufacture, newBrand, newModel)
    }

    override suspend fun sortCarsAscending(): List<CarDBModel> {
        return database.carDao().sortCarsAscending()
    }

    override suspend fun sortCarsDescending(): List<CarDBModel> {
        return database.carDao().sortCarsDescending()
    }

    override suspend fun removeCar(carToRemove: CarDBModel) {
        database.carDao().removeCar(carToRemove)
    }
}