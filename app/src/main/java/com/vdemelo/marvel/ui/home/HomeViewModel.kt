package com.vdemelo.marvel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vdemelo.marvel.domain.usecase.MarvelCharactersUseCase
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.model.toDomainModel
import com.vdemelo.marvel.ui.state.UiAction
import com.vdemelo.marvel.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val useCase: MarvelCharactersUseCase
) : ViewModel() {

    val state: StateFlow<UiState>
    val action: (UiAction) -> Unit

    val pagingDataFlow: Flow<PagingData<MarvelCharacterUi>>

    init {
        val defaultQuery = null
        val actionStateFlow = MutableSharedFlow<UiAction>()

        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = defaultQuery)) }

        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQuery = defaultQuery)) }

        pagingDataFlow = searches
            .flatMapLatest { searchMarvelCharacters(queryString = it.query) }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState()
            )

        action = { uiAction ->
            viewModelScope.launch { actionStateFlow.emit(uiAction) }
        }
    }

    fun favoriteCharacter(marvelCharacterUi: MarvelCharacterUi, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                useCase.addFavorite(marvelCharacterUi.toDomainModel())
            } else {
                useCase.removeFavorite(marvelCharacterUi.toDomainModel())
            }
        }
    }

    private fun searchMarvelCharacters(queryString: String?): Flow<PagingData<MarvelCharacterUi>> {
        val newQuery = if (queryString == "") null else queryString //TODO quando apago o texto e dou pesquisar ele n retorna nada
        return useCase.getMarvelCharactersPager(newQuery).map { pager ->
            pager.map { marvelCharacter ->
                MarvelCharacterUi(marvelCharacter)
            }
        }
    }
}
