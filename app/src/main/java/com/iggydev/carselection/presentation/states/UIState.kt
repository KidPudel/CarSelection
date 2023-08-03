package com.iggydev.carselection.presentation.states

sealed class UIState {
    object Empty : UIState()
    object Loading : UIState()
    object Loaded : UIState()
    class Error(val message: String) : UIState()
}
