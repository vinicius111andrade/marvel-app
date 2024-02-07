package com.vdemelo.marvel.domain.entity.model

class MarvelCharacter(
    val id  : Int?,
    val name : String?,
    val description : String?,
    val modified : String?,
    val urls : List<Url>,
    val thumbnail : Image?,
)
