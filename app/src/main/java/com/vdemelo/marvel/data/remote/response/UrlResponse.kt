package com.vdemelo.marvel.data.remote.response

import com.google.gson.annotations.SerializedName
import com.vdemelo.marvel.domain.entity.model.Url

class UrlResponse(
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
