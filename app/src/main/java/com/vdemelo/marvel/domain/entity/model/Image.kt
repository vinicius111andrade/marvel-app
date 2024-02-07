package com.vdemelo.marvel.domain.entity.model

class Image(
    private val path : String?,
    private val extension : String?
) {
    fun getUrl(): String = "$path.$extension"
}
