package com.vdemelo.marvel.data.local.repository

import com.vdemelo.marvel.data.local.db.MarvelFavoritesDataBase
import com.vdemelo.marvel.data.mappers.toDomainModel
import com.vdemelo.marvel.data.mappers.toEntity
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelFavoritesLocalRepository

class MarvelFavoritesLocalRepositoryImpl(
    private val favoritesDb: MarvelFavoritesDataBase
): MarvelFavoritesLocalRepository {

    override suspend fun selectAll(): List<MarvelCharacter> {
        return favoritesDb.dao.selectAll().map { it.toDomainModel() }
    }

    override suspend fun upsert(marvelCharacter: MarvelCharacter) {
        favoritesDb.dao.upsert(marvelCharacter.toEntity())
    }

    override suspend fun deleteById(id: Int) {
        favoritesDb.dao.deleteById(id)
    }
}
