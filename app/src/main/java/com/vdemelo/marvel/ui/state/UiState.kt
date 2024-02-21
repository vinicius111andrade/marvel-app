package com.vdemelo.marvel.ui.state

sealed class UiState {
    data object Success : UiState()
    data object Empty : UiState()
    data object Loading : UiState()
    class Error(val message: String?) : UiState()
}
