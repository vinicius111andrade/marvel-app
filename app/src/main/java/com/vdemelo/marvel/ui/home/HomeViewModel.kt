package com.vdemelo.marvel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.model.toEntity
import com.vdemelo.marvel.ui.state.PagingAction
import com.vdemelo.marvel.ui.state.PagingState
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
    private val repository: MarvelCharactersRepository
) : ViewModel() {

    val state: StateFlow<PagingState>
    val action: (PagingAction) -> Unit

    val pagingDataFlow: Flow<PagingData<MarvelCharacterUi>>

    init {
        val defaultQuery = null
        val actionStateFlow = MutableSharedFlow<PagingAction>()

        val searches = actionStateFlow
            .filterIsInstance<PagingAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(PagingAction.Search(query = defaultQuery)) }

        val queriesScrolled = actionStateFlow
            .filterIsInstance<PagingAction.Scroll>()
            .distinctUntilChanged()
            // This is shared to keep the flow "hot" while caching the last query scrolled,
            // otherwise each flatMapLatest invocation would lose the last query scrolled.
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(PagingAction.Scroll(currentQuery = defaultQuery)) }

        pagingDataFlow = searches
            .flatMapLatest { searchMarvelCharacters(queryString = it.query) }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            PagingState(
                query = search.query,
//                lastQueryScrolled = scroll.currentQuery, //TODO acho q nem vou usar
                // If the search query matches the scroll query, the user has scrolled
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = PagingState()
            )

        action = { uiAction ->
            viewModelScope.launch { actionStateFlow.emit(uiAction) }
        }
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

    private fun searchMarvelCharacters(queryString: String?): Flow<PagingData<MarvelCharacterUi>> {
        val newQuery = if (queryString == "") null else queryString //TODO quando apago o texto e dou pesquisar ele n retorna nada
        return repository.getMarvelCharactersPager(newQuery).map { pager ->
            pager.map { marvelCharacter ->
                MarvelCharacterUi(marvelCharacter)
            }
        }
    }
}
