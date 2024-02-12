package com.vdemelo.marvel.domain.model

private const val UNKNOWN_ERROR = "An unknown error occurred."

sealed class AsyncState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : AsyncState<T>(data)

    class Error<T>(
        data: T? = null,
        message: String = UNKNOWN_ERROR
    ) : AsyncState<T>(data, message)
}
