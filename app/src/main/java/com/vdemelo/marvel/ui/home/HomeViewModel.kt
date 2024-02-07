package com.vdemelo.marvel.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdemelo.marvel.domain.entity.RequestState
import com.vdemelo.marvel.domain.entity.model.CharacterDataWrapper
import com.vdemelo.marvel.domain.entity.model.MarvelCharacter
import com.vdemelo.marvel.domain.usecase.CharactersUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 30
private const val INITIAL_PAGE = 0

class HomeViewModel(
    private val charactersUseCase: CharactersUseCase
) : ViewModel() {

    //TODO - fazer um filtro local, antes de fazer o request, ou n pq vai estar paginado. se eu tiver a lista toda eu posso pesquisar local

    private var currentPage: Int = INITIAL_PAGE
    private var currentSearch: String? = null
    private var lastJob: Job? = null
//    private val searchableList: MutableList<MarvelCharacter> = mutableListOf()

    private fun offset() = currentPage * PAGE_SIZE //TODO ver se é isso msm

    private val _list = MutableLiveData<List<MarvelCharacter>>()
    val list: LiveData<List<MarvelCharacter>> = _list

    fun request(searchName: String? = null) {
        lastJob?.cancel()
        if (searchName != null && searchName != currentSearch) {
            currentSearch = searchName
            resetPage()
            _list.value = listOf()
        }
        lastJob = viewModelScope.launch {
            delay(500L)

            when(
                val requestState: RequestState<CharacterDataWrapper> = charactersUseCase.fetchCharacters(
                    searchName = searchName,
                    pageSize = PAGE_SIZE,
                    offset = offset()
                )
            ) {
                is RequestState.Success -> {
//                    searchableList.addAll(requestState.data?.data?.charactersList.nonNullOrEmpty())
                    updatePage()
                }
                is RequestState.Error -> {
                    //TODO talver usar um view state
                }
            }
        }
    }

    private fun updatePage() = currentPage++
    private fun resetPage() { currentPage = INITIAL_PAGE }

//    private fun searchCache(searchName: String): List<MarvelCharacter> {
//        //TODO
//        return listOf()
//    }

}
