package com.vdemelo.marvel.data.remote.api

import com.vdemelo.marvel.BuildConfig
import com.vdemelo.marvel.data.remote.response.CharacterDataWrapperResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apiKey") apiKey: String = BuildConfig.publicApiKey,
        @Query("nameStartsWith") nameStartsWith: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
    ): CharacterDataWrapperResponse

}
