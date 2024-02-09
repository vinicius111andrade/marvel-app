package com.vdemelo.marvel.domain.entity.model

private const val HTTPS = "https"
private const val DOT = "."
private const val COLON_CHAR = ':'

class Image(
    private val path : String?,
    private val extension : String?
) {
    fun getUrl(): String {
        val pathWithoutHttp = path?.dropWhile { it != COLON_CHAR }
        val pathWithHttps = HTTPS + pathWithoutHttp
        return pathWithHttps + DOT + extension
    }
}
