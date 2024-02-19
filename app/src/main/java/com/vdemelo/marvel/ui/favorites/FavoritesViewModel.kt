package com.vdemelo.marvel.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.model.toEntity
import com.vdemelo.marvel.ui.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: MarvelCharactersRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.Loading())
    val uiState: LiveData<UiState> get() = _uiState

    suspend fun getFavoritesFlow(): Flow<List<MarvelCharacterUi>> {
        _uiState.postValue(UiState.Loading())
        val flow: Flow<List<MarvelCharacterUi>> = try {
            val result = repository.getAllFavoritesFlow().map { newList ->
                newList.map { MarvelCharacterUi(it) }
            }
            _uiState.postValue(UiState.Success())
            result
        } catch (e: Exception) {
            _uiState.postValue(UiState.Error(e.message))
            flowOf<List<MarvelCharacterUi>>()
        }
        return flow
    }

    fun favoriteCharacter(marvelCharacterUi: MarvelCharacterUi, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                repository.addFavorite(marvelCharacterUi.toEntity())
            } else {
                repository.removeFavorite(marvelCharacterUi.toEntity())
            }
        }
    }
}
