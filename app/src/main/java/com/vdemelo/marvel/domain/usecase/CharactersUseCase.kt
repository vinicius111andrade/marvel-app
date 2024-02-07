package com.vdemelo.marvel.domain.usecase

import com.vdemelo.marvel.domain.entity.RequestStatus
import com.vdemelo.marvel.domain.entity.model.CharacterDataWrapper
import com.vdemelo.marvel.domain.repository.MarvelRemoteRepository

class CharactersUseCase(
    private val remoteRepository: MarvelRemoteRepository
) {

    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): RequestStatus<CharacterDataWrapper> {
        val model = try {
            remoteRepository.fetchCharacters(
                searchName = searchName,
                pageSize = pageSize,
                offset = offset
            )
        } catch (e: Exception) {
            return RequestStatus.Error()
        }
        //TODO treat different status codes, status code 409 is an error and has msgs
        return RequestStatus.Success(data = model)
    }

}
