package com.vdemelo.marvel.data.remote.api

import com.vdemelo.marvel.data.remote.NetworkConstants.PUBLIC_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apiKey") apiKey: String = PUBLIC_API_KEY //TODO ver como tratar isso
    ): String

}
