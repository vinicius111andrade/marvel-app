package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vdemelo.marvel.domain.model.MarvelCharacter

class MarvelCharacterDto(
    @SerializedName("id ") val id  : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("modified") val modified : String?, //TODO will I use this?
    @SerializedName("urls") val urls : List<UrlDto?>?, //TODO probably not gonna use this
    @SerializedName("thumbnail") val thumbnail : ThumbnailDto?,
) {

    fun toModel(): MarvelCharacter {
        return MarvelCharacter(
            id = id ?: 0, //TODO
            name = name,
            description = description,
            thumbnailUrl = null, //TODO
            isFavorite = false //TODO
        )
    }
}
