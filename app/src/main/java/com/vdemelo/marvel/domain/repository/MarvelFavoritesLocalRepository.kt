package com.vdemelo.marvel.domain.repository

import com.vdemelo.marvel.domain.model.MarvelCharacter

interface MarvelFavoritesLocalRepository {

    suspend fun selectAll(): List<MarvelCharacter>

    suspend fun upsert(marvelCharacter: MarvelCharacter)

    suspend fun deleteById(id: Int)
}
