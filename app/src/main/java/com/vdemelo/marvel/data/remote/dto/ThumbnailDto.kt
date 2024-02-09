package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName

class ThumbnailDto(
    @SerializedName("path") val path : String?,
    @SerializedName("extension") val extension : String?
)
