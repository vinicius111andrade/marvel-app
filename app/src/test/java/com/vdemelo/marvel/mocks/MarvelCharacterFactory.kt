package com.vdemelo.marvel.mocks

import com.vdemelo.marvel.ui.model.MarvelCharacterUi

class MarvelCharacterFactory {
    private var counter: Long = 0

    fun createCharacterList(size: Int): List<MarvelCharacterUi> {
        return buildList<MarvelCharacterUi> {
            var i = size
            while (i > 0) {
                add(createCharacter())
                i--
            }
        }
    }

    fun createCharacter(
        charSum: Long? = null,
        isFavorite: Boolean = false
    ): MarvelCharacterUi {
        val seed: Long = if (charSum == null) {
            counter++
            counter
        } else {
            charSum
        }
        return MarvelCharacterUi(
            charSum = seed,
            id = null,
            name = "Char Name $seed",
            description = "Very nice story $seed",
            thumbnailUrl = null,
            isFavorite = isFavorite
        )
    }
}
