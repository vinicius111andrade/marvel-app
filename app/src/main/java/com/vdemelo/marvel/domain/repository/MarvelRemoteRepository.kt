package com.vdemelo.marvel.domain.repository

import com.vdemelo.marvel.domain.entity.model.CharacterDataWrapper

interface MarvelRemoteRepository {

    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): CharacterDataWrapper
}
