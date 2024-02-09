package com.vdemelo.marvel.data.local.repository

import com.vdemelo.marvel.data.local.db.MarvelFavoritesDataBase
import com.vdemelo.marvel.data.mappers.toDomainModel
import com.vdemelo.marvel.data.mappers.domainModelToEntity
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelFavoritesLocalRepository

class MarvelFavoritesLocalRepositoryImpl(
    private val favoritesDb: MarvelFavoritesDataBase
): MarvelFavoritesLocalRepository {

    override suspend fun selectAll(): List<MarvelCharacter> {
        return favoritesDb.dao.selectAll().map { it.toDomainModel() }
    }

    override suspend fun upsert(marvelCharacter: MarvelCharacter) {
        favoritesDb.dao.upsert(marvelCharacter.domainModelToEntity())
    }

    override suspend fun deleteByCharSum(charSum: Long) {
        favoritesDb.dao.deleteByCharSum(charSum)
    }
}
