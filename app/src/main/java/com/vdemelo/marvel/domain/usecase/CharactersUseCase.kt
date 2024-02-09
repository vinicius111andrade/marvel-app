package com.vdemelo.marvel.domain.usecase

import com.vdemelo.marvel.domain.request.RequestState
import com.vdemelo.marvel.domain.model.CharacterDataWrapper
import com.vdemelo.marvel.domain.repository.MarvelRemoteRepository

class CharactersUseCase(
    private val remoteRepository: MarvelRemoteRepository
) {

    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): RequestState<CharacterDataWrapper> {
        val model = try {
            remoteRepository.fetchCharacters(
                searchName = searchName,
                pageSize = pageSize,
                offset = offset
            )
        } catch (e: Exception) {
            return RequestState.Error()
        }
        //TODO antes de retornar preciso checar do BD local se o item é fav ou n
        //TODO treat different status codes, status code 409 is an error and has msgs
        return RequestState.Success(data = model)
    }

}
