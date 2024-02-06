package com.vdemelo.marvel.data.remote.response

import com.google.gson.annotations.SerializedName

class CharactersDataContainerResponse(
    @SerializedName("offset") val offset : Int?,
    @SerializedName("limit") val limit : Int?,
    @SerializedName("total") val total : Int?,
    @SerializedName("count") val count : Int?,
    @SerializedName("results") val charactersList : List<CharacterResponse?>?,
)
