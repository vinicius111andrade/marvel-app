package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName

class CharactersDataContainerDto(
    @SerializedName("offset") val offset : Int?,
    @SerializedName("limit") val limit : Int?,
    @SerializedName("total") val total : Int?,
    @SerializedName("count") val count : Int?,
    @SerializedName("results") val charactersList : List<MarvelCharacterDto?>?,
)
