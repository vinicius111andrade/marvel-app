package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.domain.model.CharactersDataContainer

class CharactersDataContainerDto(
    @SerializedName("offset") val offset : Int?,
    @SerializedName("limit") val limit : Int?,
    @SerializedName("total") val total : Int?,
    @SerializedName("count") val count : Int?,
    @SerializedName("results") val charactersList : List<MarvelCharacterDto?>?,
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
