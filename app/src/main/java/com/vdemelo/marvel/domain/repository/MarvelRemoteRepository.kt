package com.vdemelo.marvel.domain.repository

import com.vdemelo.marvel.data.remote.response.CharacterDataWrapperResponse

interface MarvelRemoteRepository {

    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): CharacterDataWrapperResponse

}
