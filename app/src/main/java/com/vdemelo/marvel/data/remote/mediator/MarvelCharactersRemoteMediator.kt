package com.vdemelo.marvel.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.vdemelo.marvel.data.local.db.MarvelFavoritesDataBase
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.data.remote.api.MarvelApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_OFFSET = 0

@OptIn(ExperimentalPagingApi::class)
class MarvelCharactersRemoteMediator(
    private val marvelDb: MarvelFavoritesDataBase, //TODO see how DI will work
    private val marvelApi: MarvelApi
): RemoteMediator<Int, MarvelCharacterEntity>() {

    private var currentOffset: Int = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MarvelCharacterEntity>
    ): MediatorResult {
        return try {
            //TODO o que ele chama de loadkey eu vou chamar de offset
            val offset = when(loadType) {
                LoadType.REFRESH -> STARTING_OFFSET
                LoadType.PREPEND -> return  MediatorResult.Success(
                    endOfPaginationReached = true //TODO pq?
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        STARTING_OFFSET
                    } else {
                        currentOffset + state.config.pageSize //TODO como vou saber qual foi o ultimo?
                    }
                }
            }
            currentOffset = offset

            val response = marvelApi.getCharacters()

            MediatorResult.Success(endOfPaginationReached = false) //TODO
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}
