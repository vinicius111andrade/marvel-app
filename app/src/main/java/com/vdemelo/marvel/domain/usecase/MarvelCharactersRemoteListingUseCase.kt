package com.vdemelo.marvel.domain.usecase

import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelRemoteRepository
import com.vdemelo.marvel.domain.request.AsyncState

class MarvelCharactersRemoteListingUseCase(
    private val remoteRepository: MarvelRemoteRepository
) {
    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        pageNumber: Int
    ): AsyncState<List<MarvelCharacter>> {
        val remoteEntities: List<MarvelCharacterEntity> = try {
            remoteRepository.fetchCharacters(
                searchName = searchName,
                pageSize = pageSize,
                offset = pageNumber * pageSize
            )
        } catch (e: Exception) {
            return AsyncState.Error()
        }
        val marvelCharacters: List<MarvelCharacter> = remoteEntities.map { MarvelCharacter(it) }
        return AsyncState.Success(data = marvelCharacters)
    }

}
