package com.vdemelo.marvel.domain.repository

import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity

interface MarvelRemoteRepository {

    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): List<MarvelCharacterEntity>
}
