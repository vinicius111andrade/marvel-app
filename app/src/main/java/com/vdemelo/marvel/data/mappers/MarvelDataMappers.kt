package com.vdemelo.marvel.data.mappers

import com.vdemelo.common.extensions.isNotNullOrBlank
import com.vdemelo.common.extensions.toCharSum
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import com.vdemelo.marvel.data.remote.dto.MarvelCharacterDto
import com.vdemelo.marvel.data.remote.dto.ThumbnailDto

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
        thumbnailUrl = thumbnailUrl,
        isFavorite = false
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
