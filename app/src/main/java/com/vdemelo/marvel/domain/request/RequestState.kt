package com.vdemelo.marvel.domain.request

//TODO remove this
sealed class RequestState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : RequestState<T>(data)

    class Error<T>(
        data: T? = null,
        message: String = DefaultErrors.UNKNOWN_ERROR.message
    ) : RequestState<T>(data, message)
}
