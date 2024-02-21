package com.vdemelo.marvel.ui.model

import android.os.Parcelable
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelCharacterUi(
    val charSum: Long,
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnailUrl: String?,
    val isFavorite: Boolean
) : Parcelable {
    constructor(entity: MarvelCharacterEntity) : this(
        charSum = entity.charSum,
        id = entity.id,
        name = entity.name,
        description = entity.description,
        thumbnailUrl = entity.thumbnailUrl,
        isFavorite = entity.isFavorite
    )
}

fun MarvelCharacterUi.toEntity(): MarvelCharacterEntity {
    return MarvelCharacterEntity(
        charSum = charSum,
        id = id,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl,
        isFavorite = isFavorite
    )
}
