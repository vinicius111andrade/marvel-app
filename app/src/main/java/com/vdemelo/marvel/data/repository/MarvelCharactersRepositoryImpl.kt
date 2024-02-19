package com.vdemelo.marvel.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vdemelo.marvel.data.local.db.MarvelCharactersDataBase
import com.vdemelo.marvel.data.local.db.MarvelFavoritesDataBase
import com.vdemelo.marvel.data.mediator.MarvelCharactersRemoteMediator
import com.vdemelo.marvel.data.remote.PagingConstants
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MarvelCharactersRepositoryImpl(
    private val api: MarvelApi,
    private val pagingDb: MarvelCharactersDataBase,
    private val favoritesDb: MarvelFavoritesDataBase
) : MarvelCharactersRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMarvelCharactersPager(
        searchName: String?
    ): Flow<PagingData<MarvelCharacterEntity>> {
        Log.d("MarvelRepository", "New query: $searchName")

        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = searchName?.let {
            "%${it.replace(' ', '%')}%"
        }
        val pagingSourceFactory = {
            if (dbQuery != null) {
                pagingDb.itemsDao.pageByName(dbQuery)
            } else {
                pagingDb.itemsDao.pageAll()
            }
        }

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
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun addFavorite(marvelCharacter: MarvelCharacterEntity) {
        withContext(Dispatchers.IO) {
            marvelCharacter.isFavorite = true
            favoritesDb.favoritesDao.upsert(marvelCharacter)
            pagingDb.itemsDao.insert(marvelCharacter)
        }
    }

    override suspend fun removeFavorite(marvelCharacter: MarvelCharacterEntity) {
        withContext(Dispatchers.IO) {
            marvelCharacter.isFavorite = false
            favoritesDb.favoritesDao.deleteByCharSum(marvelCharacter.charSum)
            pagingDb.itemsDao.insert(marvelCharacter)
        }
    }

    override suspend fun getAllFavoritesFlow(): Flow<List<MarvelCharacterEntity>> {
        return withContext(Dispatchers.IO) {
            favoritesDb.favoritesDao.allFavoritesFlow()
        }
    }
}
