package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName

class UrlDto(
    @SerializedName("type") val type : String?,
    @SerializedName("url") val url : String?
)
