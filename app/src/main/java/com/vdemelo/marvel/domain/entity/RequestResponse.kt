package com.vdemelo.marvel.domain.entity

sealed class RequestResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : RequestResponse<T>(data)

    class Error<T>(
        data: T? = null,
        message: String = DefaultErrors.UNKNOWN_ERROR.message
    ) : RequestResponse<T>(data, message)
}
