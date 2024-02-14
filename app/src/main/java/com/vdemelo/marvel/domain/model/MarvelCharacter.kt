package com.vdemelo.marvel.domain.model

class MarvelCharacter(
    val charSum: Long,
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnailUrl: String?,
    var isFavorite: Boolean
)
