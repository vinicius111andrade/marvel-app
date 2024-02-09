package com.vdemelo.marvel.domain.model

class MarvelCharacter(
    val id  : Int?,
    val name : String?,
    val description : String?,
    val modified : String?,
    val urls : List<Url>,
    val thumbnail : Image?,
    val isFavorite: Boolean = false
)
