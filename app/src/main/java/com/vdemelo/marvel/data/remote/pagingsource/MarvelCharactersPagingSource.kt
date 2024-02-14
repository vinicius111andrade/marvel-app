package com.vdemelo.marvel.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.data.mappers.dtoToEntity
import com.vdemelo.marvel.data.mappers.toDomainModel
import com.vdemelo.marvel.data.remote.PagingConstants
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.data.remote.dto.CharacterDataWrapperDto
import com.vdemelo.marvel.data.remote.dto.MarvelCharacterDto
import com.vdemelo.marvel.domain.model.MarvelCharacter


class MarvelCharactersPagingSource(
    private val api: MarvelApi,
    private val query: String
) : PagingSource<Int, MarvelCharacter>() {
    override fun getRefreshKey(state: PagingState<Int, MarvelCharacter>): Int? {
        val anchorPosition: Int? = state.anchorPosition

        val currentPageObject: LoadResult.Page<Int, MarvelCharacter>? =
            anchorPosition?.let { state.closestPageToPosition(it) }

        val prevKey: Int? = currentPageObject?.prevKey
        val nextKey: Int? = currentPageObject?.nextKey

        return when {
            prevKey != null -> prevKey + 1
            nextKey != null -> nextKey + 1
            else -> null
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelCharacter> {
        val position = params.key ?: PagingConstants.STARTING_PAGE
        val offset = position * PagingConstants.PAGE_SIZE

        return try {
            val response: CharacterDataWrapperDto = api.getCharacters(
                nameStartsWith = query,
                limit = PagingConstants.PAGE_SIZE,
                offset = offset
            )
            val characterDtos: List<MarvelCharacterDto> =
                response.data?.charactersList.nonNullOrEmpty()

            LoadResult.Page( //TODO ver se isso aqui funciona bem, passar false no fav
                data = characterDtos.map { it.dtoToEntity().toDomainModel() },
                prevKey = if (position == PagingConstants.STARTING_PAGE) null else position - 1,
                nextKey = if (characterDtos.isEmpty()) null else position + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}
