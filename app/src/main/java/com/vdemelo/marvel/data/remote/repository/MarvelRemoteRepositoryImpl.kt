package com.vdemelo.marvel.data.remote.repository

import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.data.mappers.dtoToEntity
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.data.remote.dto.CharacterDataWrapperDto
import com.vdemelo.marvel.data.remote.dto.MarvelCharacterDto
import com.vdemelo.marvel.domain.repository.MarvelRemoteRepository

private const val PAGE_SIZE = 5

class MarvelRemoteRepositoryImpl(
    private val api: MarvelApi
): MarvelRemoteRepository {

    //TODO pagination logic could be here, and it receives only the page number

    override suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): List<MarvelCharacterEntity> {
        val wrapper: CharacterDataWrapperDto = api.getCharacters(
            nameStartsWith = searchName,
            limit = pageSize,
            offset = offset
        )
        val characterDtos: List<MarvelCharacterDto> =
            wrapper.data?.charactersList.nonNullOrEmpty()
        return characterDtos.map { it.dtoToEntity() }
    }
}
