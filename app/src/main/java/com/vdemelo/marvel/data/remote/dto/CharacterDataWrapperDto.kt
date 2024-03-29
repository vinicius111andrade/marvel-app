package com.vdemelo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName

class CharacterDataWrapperDto(
    @SerializedName("code") val code : Int?,
    @SerializedName("status") val status : String?,
    @SerializedName("copyright") val copyright : String?,
    @SerializedName("attributionText") val attributionText : String?,
    @SerializedName("attributionHTML") val attributionHTML : String?,
    @SerializedName("data") val data : CharactersDataContainerDto?
)
