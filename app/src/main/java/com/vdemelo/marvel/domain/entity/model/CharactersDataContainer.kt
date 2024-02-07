package com.vdemelo.marvel.domain.entity.model

import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.data.remote.response.CharactersDataContainerResponse

class CharactersDataContainer(
    val offset : Int?,
    val limit : Int?,
    val total : Int?,
    val count : Int?,
    val charactersList : List<MarvelCharacter>,
)
