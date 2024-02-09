package com.vdemelo.marvel.domain.usecase

import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelFavoritesLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarvelFavoritesUseCase(
    private val favoritesLocalRepository: MarvelFavoritesLocalRepository
) {
    suspend fun getFavorites(): List<MarvelCharacter> {
        return withContext(Dispatchers.IO) {
            favoritesLocalRepository.selectAll()
        }
    }

    suspend fun upsertFavorite(marvelCharacter: MarvelCharacter) {
        withContext(Dispatchers.IO) {
            favoritesLocalRepository.upsert(marvelCharacter)
        }
    }

    suspend fun deleteFavorite(marvelCharacter: MarvelCharacter) {
        withContext(Dispatchers.IO) {
            favoritesLocalRepository.deleteByCharSum(marvelCharacter.charSum)
        }
    }

}
