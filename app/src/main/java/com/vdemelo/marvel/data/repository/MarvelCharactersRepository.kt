package com.vdemelo.marvel.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vdemelo.marvel.data.local.db.MarvelCharactersDataBase
import com.vdemelo.marvel.data.mappers.domainModelToEntity
import com.vdemelo.marvel.data.remote.PagingConstants
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.data.remote.pagingsource.MarvelCharactersPagingSource
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow

class MarvelCharactersRepositoryImpl(
    private val api: MarvelApi,
    private val db: MarvelCharactersDataBase
) : MarvelCharactersRepository {
    override fun getMarvelCharactersPager(
        searchName: String
    ): Flow<PagingData<MarvelCharacter>> = Pager(
        config = PagingConfig(
            pageSize = PagingConstants.PAGE_SIZE,
            maxSize = PagingConstants.MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MarvelCharactersPagingSource(api, query = searchName)
        }
    ).flow

    override suspend fun upsert(marvelCharacter: MarvelCharacter) {
        db.dao.upsert(marvelCharacter.domainModelToEntity())
    }

    //TODO nesse aqui eu só vou descer o item se ele for favorito, ou algo assim
    // ou entao desce o item qlqr igual o de cima e ai controla na VM, tirando os unfav
//    override suspend fun fetchFavorites(): Flow<List<MarvelCharacterEntity>> {
//        //TODO
//        return favoritesDb.dao.selectAll().map { it.toDomainModel() }
//    }
}
