package com.iggydev.carselection.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iggydev.carselection.data.database.models.CarDBModel
import com.iggydev.carselection.presentation.irepositories.ICarsRepository
import com.iggydev.carselection.presentation.states.CarState
import com.iggydev.carselection.presentation.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(private val carsRepository: ICarsRepository) : ViewModel() {
    private val _state = mutableStateOf(CarState())
    val state: State<CarState>
        get() = _state

    private val _uiState = MutableStateFlow<UIState>(UIState.Empty)
    val uiState: StateFlow<UIState>
        get() = _uiState

    fun getCars() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            _state.value = CarState(cars = carsRepository.getAllCars())
            _uiState.value = UIState.Loaded
        }
    }

    fun getCar(id: Int) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            _state.value = CarState(car = carsRepository.getCar(id))
            _uiState.value = UIState.Loaded
        }
    }

    fun getCarsByManufacture(manufacture: String) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            _state.value = CarState(cars = carsRepository.getCarsByManufacture(manufacture))
            _uiState.value = UIState.Loaded
        }
    }

    fun getCarsByBrand(brand: String) {
        _uiState.value = UIState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            _state.value = CarState(cars = carsRepository.getCarsByBrand(brand))
            _uiState.value = UIState.Loaded
        }
    }

    fun addCar(manufacture: String, brand: String, model: String, price: Int, size: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            carsRepository.addCar(
                CarDBModel(
                    id = size + 1,
                    manufacture = manufacture,
                    brand = brand,
                    model = model,
                    price = price
                )
            )
        }
    }

    fun editCar(
        carId: Int,
        newManufacture: String,
        newBrand: String,
        newModel: String
    ) {
        viewModelScope.launch(context = Dispatchers.IO) {
            carsRepository.editCar(carId, newManufacture, newBrand, newModel)
        }
    }

    fun sortCarsAscending() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            _state.value = CarState(cars = carsRepository.sortCarsAscending())
            _uiState.value = UIState.Loaded
        }
    }

    fun sortCarsDescending() {
        _uiState.value = UIState.Loading
        viewModelScope.launch(context = Dispatchers.IO) {
            _state.value = CarState(cars = carsRepository.sortCarsDescending())
            _uiState.value = UIState.Loaded
        }
    }

    fun removeCar(carToRemove: CarDBModel) {
        viewModelScope.launch(context = Dispatchers.IO) {
            carsRepository.removeCar(carToRemove)
        }
    }
}