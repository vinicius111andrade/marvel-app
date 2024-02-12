package com.vdemelo.marvel.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdemelo.common.extensions.nonNullOrEmpty
import com.vdemelo.marvel.domain.model.MarvelCharacter
import com.vdemelo.marvel.domain.orchestrator.MarvelCharactersOrchestrator
import com.vdemelo.marvel.domain.model.AsyncState
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 5
private const val INITIAL_PAGE = 0

class HomeViewModel(
    private val orchestrator: MarvelCharactersOrchestrator
) : ViewModel() {

    private var nextPage: Int = INITIAL_PAGE
    private var currentSearch: String? = null
    private var lastJob: Job? = null

    var list = mutableStateOf<List<MarvelCharacterUi>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        requestCharactersList()
    }

    fun requestCharactersList(searchName: String? = null) {
        lastJob?.cancel()
        if (searchName != null && searchName != currentSearch) {
            currentSearch = searchName
            resetPage()
            list.value = listOf()
        }
        lastJob = viewModelScope.launch {
            delay(500L)
            when (
                val asyncState: AsyncState<List<MarvelCharacter>> =
                    orchestrator.requestCharactersPage(
                        searchName = searchName,
                        pageSize = PAGE_SIZE,
                        pageNumber = nextPage
                    )
            ) {
                is AsyncState.Success -> {
                    val results = asyncState.data.nonNullOrEmpty()
                    endReached.value = results.isEmpty()
                    nextPage++
                    loadError.value = "" //TODO melhorar logica de erro, talvez deixar só uma msg padrao na view
                    isLoading.value = false
                    this@HomeViewModel.list.value += results.map { MarvelCharacterUi(it) }
                }

                is AsyncState.Error -> {
                    loadError.value =
                        asyncState.message ?: "" //TODO na view se for "" ai eu boto msg padrao
                    isLoading.value = false
                }
            }
        }
    }

    private fun resetPage() {
        nextPage = INITIAL_PAGE
    }

}
