package com.vdemelo.marvel.domain.usecase

import com.vdemelo.marvel.domain.model.MarvelCharacter

//TODO ver se n tem um jeito melhor, pra deixar isso aqui na camada de UI só
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
