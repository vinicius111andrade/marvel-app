package com.vdemelo.marvel.ui.screens.favorites

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.orchestrator.MarvelCharactersOrchestrator
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val orchestrator: MarvelCharactersOrchestrator
) : ViewModel() {

    var isLoading: Boolean = true
    var list = mutableStateOf<List<MarvelCharacterUi>>(listOf())

    init {
        fetchFavorites()
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val favorites: List<MarvelCharacterUi> =
                    orchestrator.getFavorites().map { MarvelCharacterUi(it) }
            }
        }
    }
}
