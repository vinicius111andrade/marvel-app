package com.vdemelo.marvel.domain.model

class CharactersDataContainer(
    val offset : Int?,
    val limit : Int?,
    val total : Int?,
    val count : Int?,
    val charactersList : List<MarvelCharacter>,
)
