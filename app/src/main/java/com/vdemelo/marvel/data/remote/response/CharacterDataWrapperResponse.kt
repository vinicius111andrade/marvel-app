package com.vdemelo.marvel.data.remote.response

import com.google.gson.annotations.SerializedName
import com.vdemelo.marvel.domain.entity.model.CharacterDataWrapper

class CharacterDataWrapperResponse(
    @SerializedName("code") val code : Int?,
    @SerializedName("status") val status : String?,
    @SerializedName("copyright") val copyright : String?,
    @SerializedName("attributionText") val attributionText : String?,
    @SerializedName("attributionHTML") val attributionHTML : String?,
    @SerializedName("data") val data : CharactersDataContainerResponse?,
    @SerializedName("etag") val etag : String?,
) {

    fun toModel(): CharacterDataWrapper {
        return CharacterDataWrapper(
            code = code,
            status = status,
            copyright = copyright,
            attributionText = attributionText,
            attributionHTML = attributionHTML,
            data = data?.toModel(),
            etag = etag
        )
    }
}
