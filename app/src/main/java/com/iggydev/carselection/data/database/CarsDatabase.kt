package com.iggydev.carselection.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iggydev.carselection.data.database.dao.CarDao
import com.iggydev.carselection.data.database.models.CarDBModel

@Database(entities = [CarDBModel::class], version = 2)
abstract class CarsDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
}