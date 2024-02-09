package com.vdemelo.marvel.data.mappers

import com.vdemelo.common.extensions.isNotNullOrBlank
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.data.remote.dto.MarvelCharacterDto
import com.vdemelo.marvel.data.remote.dto.ThumbnailDto
import com.vdemelo.marvel.domain.model.MarvelCharacter

private const val HTTPS = "https"
private const val DOT = "."
private const val COLON_CHAR = ':'

fun List<MarvelCharacterDto>.toEntityList(): List<MarvelCharacterEntity> {
    val cleanList: MutableList<MarvelCharacterEntity> = mutableListOf()
    this.forEach { dto ->
        if (dto.id != null) {
            val entity = MarvelCharacterEntity(
                id = dto.id,
                name = dto.name,
                description = dto.description,
                thumbnailUrl = dto.thumbnail?.toUrl(),
                isFavorite = false //TODO
            )
            cleanList.add(entity)
        }
    }
    return cleanList
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

fun MarvelCharacterEntity.toDomainModel(): MarvelCharacter {
    return MarvelCharacter(
        id = id,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl,
        isFavorite = isFavorite
    )
}

fun MarvelCharacter.toEntity(): MarvelCharacterEntity {
    return MarvelCharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnailUrl = thumbnailUrl,
        isFavorite = isFavorite
    )
}
