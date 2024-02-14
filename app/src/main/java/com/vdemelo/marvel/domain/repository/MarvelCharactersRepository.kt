package com.vdemelo.marvel.domain.repository

import androidx.paging.PagingData
import com.vdemelo.marvel.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {
    fun getMarvelCharactersPager(searchName: String): Flow<PagingData<MarvelCharacter>>

    suspend fun upsert(marvelCharacter: MarvelCharacter)
}
