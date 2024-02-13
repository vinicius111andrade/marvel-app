package com.vdemelo.marvel.domain.model

import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity

class MarvelCharacter(
    val charSum: Long,
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnailUrl: String?,
    val isFavorite: Boolean
) {
    constructor(entity: MarvelCharacterEntity) : this(
        charSum = entity.charSum,
        id = entity.id,
        name = entity.name,
        description = entity.description,
        thumbnailUrl = entity.thumbnailUrl,
        isFavorite = entity.isFavorite
    )
}

fun MarvelCharacter.copy(isFavorite: Boolean): MarvelCharacter {
    return MarvelCharacter(
        charSum = charSum,
        id = id,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl,
        isFavorite = isFavorite
    )
}
