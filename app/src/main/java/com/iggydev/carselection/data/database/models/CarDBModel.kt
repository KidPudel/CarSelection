package com.iggydev.carselection.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class CarDBModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "car_manufacture") val manufacture: String?,
    @ColumnInfo(name = "car_brand") val brand: String?,
    @ColumnInfo(name = "car_model") val model: String?,
    @ColumnInfo(name = "car_price") val price: Int?
)
