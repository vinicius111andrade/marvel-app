package com.vdemelo.marvel.domain.request

//TODO remove this
sealed class AsyncState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : AsyncState<T>(data)

    class Error<T>(
        data: T? = null,
        message: String = DefaultErrors.UNKNOWN_ERROR.message
    ) : AsyncState<T>(data, message)
}
