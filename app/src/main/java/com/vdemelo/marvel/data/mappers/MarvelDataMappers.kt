package com.vdemelo.marvel.data.mappers

import com.vdemelo.common.extensions.isNotNullOrBlank
import com.vdemelo.common.extensions.toCharSum
import com.vdemelo.marvel.data.local.entity.FavoriteEntity
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.data.remote.dto.MarvelCharacterDto
import com.vdemelo.marvel.data.remote.dto.ThumbnailDto
import com.vdemelo.marvel.domain.model.MarvelCharacter

private const val HTTPS = "https"
private const val DOT = "."
private const val COLON_CHAR = ':'

fun MarvelCharacterDto.dtoToEntity(): MarvelCharacterEntity {
    val thumbnailUrl = thumbnail?.toUrl()
    val charSum = listOf<String?>(
        id.toString(),
        name,
        thumbnailUrl
    ).toCharSum()
    return MarvelCharacterEntity(
        charSum = charSum,
        id = id,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl
    )
}

private fun ThumbnailDto.toUrl(): String? {
    return if (path.isNotNullOrBlank() && extension.isNotNullOrBlank()) {
        val pathWithoutHttp: String? = path?.dropWhile { it != COLON_CHAR }
        val pathWithHttps: String = HTTPS + pathWithoutHttp
        pathWithHttps + DOT + extension
    } else {
        null
    }
}

fun MarvelCharacterEntity.toDomainModel(favoriteEntity: FavoriteEntity?): MarvelCharacter {
    return MarvelCharacter(
        charSum = charSum,
        id = id,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl,
        isFavorite = (favoriteEntity?.charSum == charSum)
    )
}

//TODO remove?
fun MarvelCharacterEntity.toFavorite(): FavoriteEntity = FavoriteEntity(charSum = charSum)

//TODO remove?
fun MarvelCharacter.domainModelToEntity(): MarvelCharacterEntity {
    return MarvelCharacterEntity(
        charSum = charSum,
        id = id,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl
    )
}

fun MarvelCharacter.toFavorite(): FavoriteEntity = FavoriteEntity(charSum = charSum)
