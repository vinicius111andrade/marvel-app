package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.domain.entity.model.MarvelCharacter

class MarvelCharacterDto(
    @SerializedName("id ") val id  : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("modified") val modified : String?, //TODO will I use this?
    @SerializedName("urls") val urls : List<UrlDto?>?,
    @SerializedName("thumbnail") val thumbnail : ThumbnailDto?,
) {

    fun toModel(): MarvelCharacter {
        return MarvelCharacter(
            id = id,
            name = name,
            description = description,
            modified = modified,
            urls = urls.nonNullOrEmpty().map { it.toModel() },
            thumbnail = thumbnail?.toModel()
        )
    }
}
