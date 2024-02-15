package com.vdemelo.marvel.ui.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdemelo.marvel.domain.usecase.MarvelCharactersUseCase
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.model.toDomainModel
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val useCase: MarvelCharactersUseCase
) : ViewModel() {

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun setFavoriteState(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

    fun favoriteCharacter(marvelCharacterUi: MarvelCharacterUi, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                useCase.addFavorite(marvelCharacterUi.toDomainModel())
            } else {
                useCase.removeFavorite(marvelCharacterUi.toDomainModel())
            }
            _isFavorite.postValue(isFavorite)
        }
    }
}
