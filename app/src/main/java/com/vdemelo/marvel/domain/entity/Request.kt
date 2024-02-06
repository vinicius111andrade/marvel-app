package com.vdemelo.marvel.domain.entity

sealed class Request<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Request<T>(data)

    class Error<T>(
        data: T? = null,
        message: String = DefaultErrors.UNKNOWN_ERROR.message
    ) : Request<T>(data, message)
}
