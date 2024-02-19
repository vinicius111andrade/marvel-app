package com.vdemelo.marvel.ui.state

sealed class UiState {
    class Success() : UiState()
    class Loading() : UiState()
    class Error(val message: String?) : UiState()
}
