package com.vdemelo.marvel.domain.repository

import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {

    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): Flow<List<MarvelCharacterEntity>>

    suspend fun fetchFavorites(): Flow<List<MarvelCharacterEntity>>

    suspend fun upsert(marvelCharacter: MarvelCharacter)

    suspend fun deleteByCharSum(charSum: Long)

}
