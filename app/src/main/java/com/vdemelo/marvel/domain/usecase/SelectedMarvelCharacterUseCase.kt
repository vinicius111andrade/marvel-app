package com.vdemelo.marvel.domain.usecase

import com.vdemelo.marvel.domain.model.MarvelCharacter

class SelectedMarvelCharacterUseCase {
    private var selectedCharacter: MarvelCharacter? = null

    fun cacheSelectedCharacter(character: MarvelCharacter) {
        selectedCharacter = character
    }

    fun getSelectedCharacter(): MarvelCharacter? {
        return selectedCharacter
    }

    fun clearSelectedCharacter() {
        selectedCharacter = null
    }
}
