package com.vdemelo.marvel

import androidx.paging.PagingData
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow

class MarvelCharactersRepositoryMock: MarvelCharactersRepository {
    override fun getMarvelCharactersPager(searchName: String?): Flow<PagingData<MarvelCharacterEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun addFavorite(marvelCharacter: MarvelCharacterEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavorite(marvelCharacter: MarvelCharacterEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFavoritesFlow(): Flow<List<MarvelCharacterEntity>> {
        TODO("Not yet implemented")
    }
}
