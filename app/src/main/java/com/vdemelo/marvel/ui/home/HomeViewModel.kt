package com.vdemelo.marvel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.model.toEntity
import com.vdemelo.marvel.ui.state.ListAction
import com.vdemelo.marvel.ui.state.ListState
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

    val listStateFlow: StateFlow<ListState>
    val listAction: (ListAction) -> Unit

    val pagingDataFlow: Flow<PagingData<MarvelCharacterUi>>

    init {
        val defaultQuery = null
        val listActionMutableSharedFlow = MutableSharedFlow<ListAction>()

        val searchActionFlow: Flow<ListAction.Search> = listActionMutableSharedFlow
            .filterIsInstance<ListAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(ListAction.Search(query = defaultQuery)) }

        val scrollActionFlow: Flow<ListAction.Scroll> = listActionMutableSharedFlow
            .filterIsInstance<ListAction.Scroll>()
            .distinctUntilChanged()
            // This is shared to keep the flow "hot" while caching the last query scrolled,
            // otherwise each flatMapLatest invocation would lose the last query scrolled.
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(ListAction.Scroll(currentQuery = defaultQuery)) }

        pagingDataFlow = searchActionFlow
            .flatMapLatest {
                searchMarvelCharacters(queryString = it.query)
            }.cachedIn(viewModelScope)

        listStateFlow = combine(
            searchActionFlow,
            scrollActionFlow,
            ::Pair
        ).map { (search, scroll) ->
            ListState(
                query = search.query,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ListState()
        )

        listAction = { uiAction ->
            viewModelScope.launch { listActionMutableSharedFlow.emit(uiAction) }
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
        val newQuery = if (queryString == "") null else queryString
        return repository.getMarvelCharactersPager(newQuery).map { pager ->
            pager.map { marvelCharacter ->
                MarvelCharacterUi(marvelCharacter)
            }
        }
    }
}
