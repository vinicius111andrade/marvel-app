package com.vdemelo.marvel.data.remote.response

import com.google.gson.annotations.SerializedName
import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.domain.entity.model.CharactersDataContainer

class CharactersDataContainerResponse(
    @SerializedName("offset") val offset : Int?,
    @SerializedName("limit") val limit : Int?,
    @SerializedName("total") val total : Int?,
    @SerializedName("count") val count : Int?,
    @SerializedName("results") val charactersList : List<MarvelCharacterResponse?>?,
) {

    fun toModel(): CharactersDataContainer {
        return CharactersDataContainer(
            offset = offset,
            limit = limit,
            total = total,
            count = count,
            charactersList = charactersList.nonNullOrEmpty().map { it.toModel() },
        )
    }
}
