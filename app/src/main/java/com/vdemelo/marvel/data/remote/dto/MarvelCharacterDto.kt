package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName

class MarvelCharacterDto(
    @SerializedName("id ") val id  : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("thumbnail") val thumbnail : ThumbnailDto?
)
