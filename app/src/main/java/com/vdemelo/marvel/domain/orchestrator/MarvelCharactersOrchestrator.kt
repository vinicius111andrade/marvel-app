package com.vdemelo.marvel.domain.orchestrator

import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.request.AsyncState
import com.vdemelo.marvel.domain.usecase.MarvelCharactersRemoteListingUseCase
import com.vdemelo.marvel.domain.usecase.MarvelFavoritesUseCase

class MarvelCharactersOrchestrator(
    val remoteListingUseCase: MarvelCharactersRemoteListingUseCase,
    val favoritesUseCase: MarvelFavoritesUseCase
) {

    suspend fun requestCharactersPage(
        searchName: String?,
        pageSize: Int,
        pageNumber: Int
    ): AsyncState<List<MarvelCharacter>> {
        val remoteList: List<MarvelCharacter> = try {
            remoteListingUseCase.fetchCharacters(
                searchName,
                pageSize,
                pageNumber
            ).data ?: listOf()
        } catch (e: Exception) {
            return AsyncState.Error()
        }

        val favoritesList: List<MarvelCharacter> = try {
            favoritesUseCase.getFavorites()
        } catch (e: Exception) {
            return AsyncState.Error()
        }

        val jointList = remoteList.map { remoteItem ->
            favoritesList.find { it.charSum == remoteItem.charSum } ?: remoteItem
        }

        return AsyncState.Success(data = jointList)
    }

    suspend fun updateFavorite(character: MarvelCharacter, isFavorite: Boolean) {
        if (isFavorite) {
            val favorite = MarvelCharacter(
                charSum = character.charSum,
                id = character.id,
                name = character.name,
                description = character.description,
                thumbnailUrl = character.thumbnailUrl,
                isFavorite = true
            )
            favoritesUseCase.upsertFavorite(favorite)
        } else {
            favoritesUseCase.deleteFavorite(character)
        }
    }

    suspend fun getFavorites(): List<MarvelCharacter> {
        return favoritesUseCase.getFavorites()
    }

}
