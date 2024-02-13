package com.vdemelo.marvel.data.repository

import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.data.local.db.characters.MarvelCharactersDataBase
import com.vdemelo.marvel.data.local.db.favorites.FavoritesDataBase
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.data.mappers.domainModelToEntity
import com.vdemelo.marvel.data.mappers.dtoToEntity
import com.vdemelo.marvel.data.mappers.toDomainModel
import com.vdemelo.marvel.data.mappers.toFavorite
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.data.remote.dto.CharacterDataWrapperDto
import com.vdemelo.marvel.data.remote.dto.MarvelCharacterDto
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow

class MarvelCharactersRepositoryImpl(
    private val api: MarvelApi,
    private val charactersDb: MarvelCharactersDataBase,
    private val favoritesDb: FavoritesDataBase
): MarvelCharactersRepository {

    override suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): Flow<List<MarvelCharacter>> {
        val wrapper: CharacterDataWrapperDto = api.getCharacters(
            nameStartsWith = searchName,
            limit = pageSize,
            offset = offset
        )
        val characterDtos: List<MarvelCharacterDto> =
            wrapper.data?.charactersList.nonNullOrEmpty()
        return characterDtos.map { it.dtoToEntity() }
    }

    override suspend fun fetchFavorites(): Flow<List<MarvelCharacterEntity>> {
        //TODO
        return favoritesDb.dao.selectAll().map { it.toDomainModel() }
    }

    override suspend fun upsert(marvelCharacter: MarvelCharacter) {
        favoritesDb.dao.upsert(marvelCharacter.toFavorite())
    }

    override suspend fun deleteByCharSum(charSum: Long) {
        favoritesDb.dao.deleteByCharSum(charSum)
    }

}
