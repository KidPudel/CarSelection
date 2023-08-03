package com.iggydev.carselection.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.iggydev.carselection.data.database.models.CarDBModel

@Dao
interface CarDao {
    @Transaction
    @Query("SELECT * FROM car")
    fun getAllCars(): List<CarDBModel>

    @Transaction
    @Query("SELECT * FROM car where id = :id")
    fun getCar(id: Int): CarDBModel

    @Transaction
    @Insert
    fun addCar(newCar: CarDBModel)

    @Query("UPDATE car SET car_manufacture = :newManufacture, car_brand = :newBrand, car_model = :newModel WHERE id = :carId")
    fun editCar(carId: Int, newManufacture: String, newBrand: String, newModel: String)

    @Query("SELECT * FROM car WHERE car_manufacture = :manufacture")
    fun getCarsByManufacture(manufacture: String): List<CarDBModel>

    @Query("SELECT * FROM car WHERE car_brand = :brand")
    fun getCarsByBrand(brand: String): List<CarDBModel>

    @Query("SELECT * FROM car ORDER BY car_price ASC")
    fun sortCarsAscending(): List<CarDBModel>

    @Query("SELECT * FROM car ORDER BY car_price DESC")
    fun sortCarsDescending(): List<CarDBModel>

    @Delete
    fun removeCar(carToRemove: CarDBModel)
}