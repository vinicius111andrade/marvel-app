package com.vdemelo.marvel.ui.model

import com.vdemelo.marvel.domain.model.MarvelCharacter

class MarvelCharacterUi(
    val id  : Int,
    val name : String?,
    val description : String?,
    val thumbnailUrl : String?,
    val isFavorite: Boolean
) {
    constructor(domainModel: MarvelCharacter): this(
        id = domainModel.id,
        name = domainModel.name,
        description = domainModel.description,
        thumbnailUrl = domainModel.thumbnailUrl,
        isFavorite = domainModel.isFavorite
    )
}
