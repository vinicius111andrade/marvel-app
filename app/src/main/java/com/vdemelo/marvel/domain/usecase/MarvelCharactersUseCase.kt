package com.vdemelo.marvel.domain.usecase

import android.util.Log
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelFavoritesLocalRepository
import com.vdemelo.marvel.domain.repository.MarvelRemoteRepository
import com.vdemelo.marvel.domain.request.RequestState

class MarvelCharactersUseCase(
    private val remoteRepository: MarvelRemoteRepository,
    private val favoriteCharactersLocalRepository: MarvelFavoritesLocalRepository
) {
//    private val favoritesCache: MutableList<MarvelCharacter> = mutableListOf()

    suspend fun getFavorites(): List<MarvelCharacter> {
        return favoriteCharactersLocalRepository.selectAll()
    }

    suspend fun upsertFavorite(marvelCharacter: MarvelCharacter) {
        favoriteCharactersLocalRepository.upsert(marvelCharacter)
    }

    suspend fun deleteFavorite(marvelCharacter: MarvelCharacter) {
        favoriteCharactersLocalRepository.deleteByCharSum(marvelCharacter.charSum)
    }

    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): RequestState<List<MarvelCharacter>> {

//        if (offset == 0) {
//            updateFavoritesCache()
//        }

        val remoteEntities: List<MarvelCharacterEntity> = try {
            remoteRepository.fetchCharacters(
                searchName = searchName,
                pageSize = pageSize,
                offset = offset
            )
        } catch (e: Exception) {
            return RequestState.Error()
        }

        Log.d("lista inteira vini", "lista aqui: $remoteEntities")

        val marvelCharacters: List<MarvelCharacter> = remoteEntities.map { MarvelCharacter(it) }

        //TODO erro por acessar DB na main thread
        //favoriteCharactersLocalRepository.selectAll()

        //TODO antes de retornar preciso checar do BD local se o item é fav ou n
        //TODO treat different status codes, status code 409 is an error and has msgs
        return RequestState.Success(data = marvelCharacters)
    }

//    private suspend fun updateFavoritesCache() {
//        favoritesCache.clear()
//        favoritesCache.addAll(getFavorites())
//    }

}
