package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vdemelo.marvel.domain.model.Image

class ThumbnailDto(
    @SerializedName("path") val path : String?,
    @SerializedName("extension") val extension : String?
) {

    fun toModel(): Image {
        return Image(
            path = path,
            extension = extension
        )
    }
}
