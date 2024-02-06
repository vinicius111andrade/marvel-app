package com.vdemelo.marvel.domain.usecase

import com.vdemelo.marvel.data.remote.response.CharacterDataWrapperResponse
import com.vdemelo.marvel.domain.entity.RequestResponse
import com.vdemelo.marvel.domain.repository.MarvelRemoteRepository

class CharactersUseCase(
    private val remoteRepository: MarvelRemoteRepository
) {

    suspend fun fetchCharacters(
        searchName: String?,
        pageSize: Int,
        offset: Int
    ): RequestResponse<CharacterDataWrapperResponse> {
        val response = try {
            remoteRepository.fetchCharacters(
                searchName = searchName,
                pageSize = pageSize,
                offset = offset
            )
        } catch (e: Exception) {
            return RequestResponse.Error()
        }
        //TODO treat different status codes, status code 409 is an error and has msgs
//        val model = convert to model TODO AAAA
        return RequestResponse.Success(data = response)
    }

}
