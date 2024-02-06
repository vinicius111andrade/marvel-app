package com.vdemelo.marvel.data.remote.response

import com.google.gson.annotations.SerializedName

class CharacterDataWrapperResponse(
    @SerializedName("code") val code : Int?,
    @SerializedName("status") val status : String?,
    @SerializedName("copyright") val copyright : String?,
    @SerializedName("attributionText") val attributionText : String?,
    @SerializedName("attributionHTML") val attributionHTML : String?,
    @SerializedName("data") val data : CharactersDataContainerResponse?,
    @SerializedName("etag") val etag : String?,
)
