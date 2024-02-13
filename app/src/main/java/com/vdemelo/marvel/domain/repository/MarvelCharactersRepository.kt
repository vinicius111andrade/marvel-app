package com.vdemelo.marvel.domain.repository

import androidx.paging.PagingData
import com.vdemelo.marvel.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {

//    suspend fun fetchCharacters(
//        searchName: String?,
//        pageSize: Int,
//        offset: Int
//    ): Flow<List<MarvelCharacter>>

    suspend fun getMarvelCharactersPager(searchName: String): Flow<PagingData<MarvelCharacter>>

//    suspend fun fetchFavorites(): Flow<List<MarvelCharacter>>

    suspend fun upsert(marvelCharacter: MarvelCharacter)

    suspend fun deleteByCharSum(charSum: Long)

}
