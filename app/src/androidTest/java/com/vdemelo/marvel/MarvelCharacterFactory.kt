package com.vdemelo.marvel

import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity

class MarvelCharacterFactory {
    private var counter: Long = 0

    fun createCharacter(
        charSum: Long? = null,
        isFavorite: Boolean = false
    ): MarvelCharacterEntity {
        val seed: Long = if (charSum == null) {
            counter++
            counter
        } else {
            charSum
        }
        return MarvelCharacterEntity(
            charSum = seed,
            id = null,
            name = "Char Name $seed",
            description = "Very nice story $seed",
            thumbnailUrl = null,
            isFavorite = isFavorite
        )
    }
}
