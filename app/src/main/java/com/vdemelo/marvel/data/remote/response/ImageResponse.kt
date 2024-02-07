package com.vdemelo.marvel.data.remote.response

import com.google.gson.annotations.SerializedName
import com.vdemelo.marvel.domain.entity.model.Image

class ImageResponse(
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
