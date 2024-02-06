package com.vdemelo.marvel.data.remote.response

import com.google.gson.annotations.SerializedName

class ImageResponse(
    @SerializedName("path") val path : String?,
    @SerializedName("extension") val extension : String?
)
