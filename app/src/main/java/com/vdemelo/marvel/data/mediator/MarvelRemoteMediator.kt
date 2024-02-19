package com.vdemelo.marvel.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.data.local.db.MarvelCharactersDataBase
import com.vdemelo.marvel.data.local.db.MarvelFavoritesDataBase
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import com.vdemelo.marvel.data.local.paging.RemoteKeys
import com.vdemelo.marvel.data.mappers.dtoToEntity
import com.vdemelo.marvel.data.remote.PagingConstants
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.data.remote.dto.MarvelCharacterDto
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MarvelCharactersRemoteMediator(
    private val query: String?,
    private val api: MarvelApi,
    private val pagingDb: MarvelCharactersDataBase,
    private val favoritesDb: MarvelFavoritesDataBase,
) : RemoteMediator<Int, MarvelCharacterEntity>() {

    //TODO atualizar comentario, mudei para o skip
    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MarvelCharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: PagingConstants.STARTING_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val offset: Int = page * state.config.pageSize
            val response: List<MarvelCharacterDto> = api.getCharacters(
                nameStartsWith = query,
                limit = state.config.pageSize,
                offset = offset
            ).data?.charactersList.nonNullOrEmpty()
            val endOfPaginationReached: Boolean = response.isEmpty()
            val newEntities: List<MarvelCharacterEntity> = response.map { it.dtoToEntity() }
            val newEntitiesUpdated: List<MarvelCharacterEntity> = checkForFavoritesAndUpdate(newEntities)

            pagingDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pagingDb.itemsDao.clearAll()
                    pagingDb.keysDao.clearRemoteKeys()
                }
                val prevKey = if (page == PagingConstants.STARTING_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys: List<RemoteKeys> = newEntities.map {
                    RemoteKeys(id = it.charSum, prevKey = prevKey, nextKey = nextKey)
                }
                pagingDb.itemsDao.insertAll(newEntitiesUpdated)
                pagingDb.keysDao.insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun checkForFavoritesAndUpdate(
        entities: List<MarvelCharacterEntity>
    ): List<MarvelCharacterEntity> {
        val entitiesUpdated = mutableListOf<MarvelCharacterEntity>()
        val allFavorites = mutableListOf<MarvelCharacterEntity>()
        favoritesDb.withTransaction {
            allFavorites.addAll(favoritesDb.favoritesDao.selectAll())
            entitiesUpdated.addAll(
                entities.map { entity ->
                    val isFavorite = allFavorites.find { favorite ->
                        entity.charSum == favorite.charSum
                    } != null
                    if (isFavorite) { entity.isFavorite = true }
                    entity
                }.nonNullOrEmpty()
            )
        }
        return entitiesUpdated
    }

    private suspend fun getFirstRemoteKey(
        state: PagingState<Int, MarvelCharacterEntity>
    ): RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character -> pagingDb.keysDao.getRemoteKeysById(character.charSum) }
    }

    private suspend fun getLastRemoteKey(
        state: PagingState<Int, MarvelCharacterEntity>
    ): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character -> pagingDb.keysDao.getRemoteKeysById(character.charSum) }
    }

    private suspend fun getClosestRemoteKey(
        state: PagingState<Int, MarvelCharacterEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.charSum?.let { charSum ->
                pagingDb.keysDao.getRemoteKeysById(charSum)
            }
        }
    }

}
