package com.vdemelo.marvel.domain.usecase

import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.repository.MarvelRemoteRepository
import com.vdemelo.marvel.domain.request.RequestState

class MarvelCharactersRemoteListingUseCase(
    private val remoteRepository: MarvelRemoteRepository
) {
    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): RequestState<List<MarvelCharacter>> {
        val remoteEntities: List<MarvelCharacterEntity> = try {
            remoteRepository.fetchCharacters(
                searchName = searchName,
                pageSize = pageSize,
                offset = offset
            )
        } catch (e: Exception) {
            return RequestState.Error()
        }
        val marvelCharacters: List<MarvelCharacter> = remoteEntities.map { MarvelCharacter(it) }
        return RequestState.Success(data = marvelCharacters)
    }

}
