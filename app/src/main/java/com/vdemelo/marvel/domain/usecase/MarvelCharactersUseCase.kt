package com.vdemelo.marvel.domain.usecase

import androidx.paging.PagingData
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MarvelCharactersUseCase(
    private val repository: MarvelCharactersRepository
) {
    fun getMarvelCharactersPager(
        searchName: String?
    ): Flow<PagingData<MarvelCharacter>> {
        return repository.getMarvelCharactersPager(searchName)
    }

    suspend fun addFavorite(marvelCharacter: MarvelCharacter) {
        withContext(Dispatchers.IO) {
            marvelCharacter.isFavorite = true
            repository.upsert(marvelCharacter)
        }
    }

    suspend fun removeFavorite(marvelCharacter: MarvelCharacter) {
        withContext(Dispatchers.IO) {
            marvelCharacter.isFavorite = false
            repository.upsert(marvelCharacter)
        }
    }
}
