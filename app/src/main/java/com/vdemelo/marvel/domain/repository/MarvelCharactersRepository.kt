package com.vdemelo.marvel.domain.repository

import androidx.paging.PagingData
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {
    fun getMarvelCharactersPager(searchName: String?): Flow<PagingData<MarvelCharacterEntity>>
    suspend fun addFavorite(marvelCharacter: MarvelCharacterEntity)
    suspend fun removeFavorite(marvelCharacter: MarvelCharacterEntity)
    suspend fun getAllFavoritesFlow(): Flow<List<MarvelCharacterEntity>>
}
