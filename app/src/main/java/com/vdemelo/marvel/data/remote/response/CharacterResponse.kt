package com.vdemelo.marvel.data.remote.response

import com.google.gson.annotations.SerializedName

class CharacterResponse(
    @SerializedName("id ") val id  : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("modified") val modified : String?, //TODO 2013-09-18T15:54:04-0400 it is a Date, see pattern to parse it, if it parses fine as a string dont need to bother
//    @SerializedName("resourceURI") val resourceURI : String?,
    @SerializedName("urls") val urls : List<UrlResponse?>?,
    @SerializedName("thumbnail") val thumbnail : ImageResponse?,
//    @SerializedName("comics") val comics : String?, //TODO remove, almost certain I wont use this
//    @SerializedName("stories") val stories : String?,
//    @SerializedName("events") val events : String?,
//    @SerializedName("series") val series : String?,
)
