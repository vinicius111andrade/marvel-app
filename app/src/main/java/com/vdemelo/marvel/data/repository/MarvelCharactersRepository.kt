package com.vdemelo.marvel.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vdemelo.marvel.data.local.db.characters.MarvelCharactersDataBase
import com.vdemelo.marvel.data.local.db.favorites.FavoritesDataBase
import com.vdemelo.marvel.data.mappers.toFavorite
import com.vdemelo.marvel.data.remote.PagingConstants
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.data.remote.pagingsource.MarvelCharactersPagingSource
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow

class MarvelCharactersRepositoryImpl(
    private val api: MarvelApi,
    private val charactersDb: MarvelCharactersDataBase,
    private val favoritesDb: FavoritesDataBase
) : MarvelCharactersRepository {
//    override suspend fun fetchCharacters(
//        searchName: String?,
//        pageSize: Int,
//        offset: Int
//    ): Flow<List<MarvelCharacter>> {
//        val wrapper: CharacterDataWrapperDto = api.getCharacters(
//            nameStartsWith = searchName,
//            limit = pageSize,
//            offset = offset
//        )
//        val characterDtos: List<MarvelCharacterDto> =
//            wrapper.data?.charactersList.nonNullOrEmpty()
//        return characterDtos.map { it.dtoToEntity() }
//    }

    override suspend fun getMarvelCharactersPager(
        searchName: String
    ): Flow<PagingData<MarvelCharacter>> = Pager(
        config = PagingConfig(
            pageSize = PagingConstants.PAGE_SIZE,
            maxSize = PagingConstants.MAX_SIZE,
            enablePlaceholders = false //TODO talvez isso seja util no futuro
        ),
        pagingSourceFactory = {
            MarvelCharactersPagingSource(api, query = searchName)
        }
    ).flow

    //TODO nesse aqui eu só vou descer o item se ele for favorito, ou algo assim
    // ou entao desce o item qlqr igual o de cima e ai controla na VM, tirando os unfav
//    override suspend fun fetchFavorites(): Flow<List<MarvelCharacterEntity>> {
//        //TODO
//        return favoritesDb.dao.selectAll().map { it.toDomainModel() }
//    }

    override suspend fun upsert(marvelCharacter: MarvelCharacter) {
        favoritesDb.dao.upsert(marvelCharacter.toFavorite())
    }

    override suspend fun deleteByCharSum(charSum: Long) {
        favoritesDb.dao.deleteByCharSum(charSum)
    }
}
