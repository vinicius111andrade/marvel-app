package com.vdemelo.marvel.data.remote.api

import com.vdemelo.marvel.data.remote.dto.CharacterDataWrapperDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
    ): CharacterDataWrapperDto

}
