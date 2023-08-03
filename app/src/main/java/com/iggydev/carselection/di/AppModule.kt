package com.iggydev.carselection.di

import android.app.Application
import androidx.room.Room
import com.iggydev.carselection.data.database.CarsDatabase
import com.iggydev.carselection.data.repositories.CarsRepository
import com.iggydev.carselection.presentation.irepositories.ICarsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Singleton
    @Binds
    fun getCarsRepository(carsRepository: CarsRepository): ICarsRepository

    companion object {
        @Singleton
        @Provides
        fun getDatabase(application: Application): CarsDatabase {
            return Room.databaseBuilder(
                context = application,
                klass = CarsDatabase::class.java,
                name = "cars"
            )
                .createFromAsset(databaseFilePath = "database/cars.db")
                .fallbackToDestructiveMigration()
                .build()

        }
    }
}