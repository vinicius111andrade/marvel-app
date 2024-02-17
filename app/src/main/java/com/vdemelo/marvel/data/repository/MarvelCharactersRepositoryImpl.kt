package com.vdemelo.marvel.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vdemelo.marvel.data.local.db.MarvelCharactersDataBase
import com.vdemelo.marvel.data.local.db.MarvelFavoritesDataBase
import com.vdemelo.marvel.data.mappers.domainModelToEntity
import com.vdemelo.marvel.data.mappers.toDomainModel
import com.vdemelo.marvel.data.mediator.MarvelCharactersRemoteMediator
import com.vdemelo.marvel.data.remote.PagingConstants
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.data.remote.pagingsource.MarvelCharactersPagingSource
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow

class MarvelCharactersRepositoryImpl(
    private val api: MarvelApi,
    private val pagingDb: MarvelCharactersDataBase,
    private val favoritesDb: MarvelFavoritesDataBase
) : MarvelCharactersRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMarvelCharactersPager(
        searchName: String?
    ): Flow<PagingData<MarvelCharacter>> {
        Log.d("MarvelRepository", "New query: $searchName")

        return Pager(
            config = PagingConfig(
                pageSize = PagingConstants.PAGE_SIZE,
                maxSize = PagingConstants.MAX_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = MarvelCharactersRemoteMediator(
                query = searchName,
                api = api,
                pagingDb = pagingDb,
                favoritesDb = favoritesDb
            ),
            pagingSourceFactory = {
                MarvelCharactersPagingSource(api, query = searchName)
            }
        ).flow
    }

    override suspend fun upsert(marvelCharacter: MarvelCharacter) {
        pagingDb.itemsDao.upsert(marvelCharacter.domainModelToEntity())
    }

    override suspend fun addFavorite(marvelCharacter: MarvelCharacter) {
        val entity = marvelCharacter.domainModelToEntity()
        entity.isFavorite = true
        favoritesDb.favoritesDao.upsert(entity)
        pagingDb.itemsDao.upsert(entity)
    }

    override suspend fun removeFavorite(marvelCharacter: MarvelCharacter) {
        val entity = marvelCharacter.domainModelToEntity()
        entity.isFavorite = false
        favoritesDb.favoritesDao.deleteByCharSum(entity.charSum)
        pagingDb.itemsDao.upsert(entity)
    }

    override suspend fun getAllFavorites(): List<MarvelCharacter> {
        return favoritesDb.favoritesDao.selectAll().map { it.toDomainModel() }
    }
}
