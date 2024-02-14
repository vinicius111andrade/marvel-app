package com.vdemelo.marvel.ui.model

import com.vdemelo.marvel.domain.model.MarvelCharacter

data class MarvelCharacterUi(
    val charSum: Long,
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnailUrl: String?,
    val isFavorite: Boolean
) {
    constructor(domainModel: MarvelCharacter) : this(
        charSum = domainModel.charSum,
        id = domainModel.id,
        name = domainModel.name,
        description = domainModel.description,
        thumbnailUrl = domainModel.thumbnailUrl,
        isFavorite = domainModel.isFavorite
    )
}

//TODO preciso disso?
fun MarvelCharacterUi.toDomainModel(): MarvelCharacter {
    return MarvelCharacter(
        charSum = charSum,
        id = id,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl,
        isFavorite = isFavorite
    )
}
