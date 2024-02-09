package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName

class MarvelCharacterDto(
    @SerializedName("id ") val id  : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("modified") val modified : String?, //TODO will I use this?
    @SerializedName("urls") val urls : List<UrlDto?>?, //TODO probably not gonna use this
    @SerializedName("thumbnail") val thumbnail : ThumbnailDto?,
)
