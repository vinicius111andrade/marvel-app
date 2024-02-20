package com.vdemelo.marvel.ui.state

sealed class UiState {
    data object Success : UiState()
    data object Empty : UiState()
    data object Loading : UiState()
    class Error(val message: String?) : UiState()
}

//sealed class UiState {
//    data class Success<T>(val data: T) : UiState()
//    data object Empty : UiState()
//    data object Loading : UiState()
//    class Error(val message: String?) : UiState()
//}

//TODO vou usar?
//sealed class RequestStatus<T>(
//    val data: T? = null,
//    val message: String? = null
//) {
//    class Success<T>(data: T) : RequestStatus<T>(data)
//    class Error<T>(data: T? = null, message: String) : RequestStatus<T>(data, message)
//}
