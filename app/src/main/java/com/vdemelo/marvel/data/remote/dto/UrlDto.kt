package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vdemelo.marvel.domain.model.Url

class UrlDto(
    @SerializedName("type") val type : String?,
    @SerializedName("url") val url : String?
) {

    fun toModel(): Url {
        return Url(
            type = type,
            url = url
        )
    }
}
