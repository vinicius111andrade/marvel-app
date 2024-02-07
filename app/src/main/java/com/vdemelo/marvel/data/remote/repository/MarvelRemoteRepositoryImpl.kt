package com.vdemelo.marvel.data.remote.repository

import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.domain.entity.model.CharacterDataWrapper
import com.vdemelo.marvel.domain.repository.MarvelRemoteRepository

class MarvelRemoteRepositoryImpl(
    private val api: MarvelApi
): MarvelRemoteRepository {

    override suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): CharacterDataWrapper {
        return api.getCharacters(
            nameStartsWith = searchName,
            limit = pageSize,
            offset = offset
        ).toModel()
    }
}
