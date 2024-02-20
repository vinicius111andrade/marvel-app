package com.vdemelo.marvel.ui.favorites

import MainDispatcherRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import com.vdemelo.marvel.mocks.MarvelCharacterFactory
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.model.toEntity
import com.vdemelo.marvel.ui.state.UiState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.IOException
import kotlin.test.assertEquals


class FavoritesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: FavoritesViewModel
    private val repository: MarvelCharactersRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `When getFavorites() Called Should Update Favorites LiveData`() {
        val factory = MarvelCharacterFactory()
        val list = factory.createCharacterList(size = 10)
        val entityList = list.map { it.toEntity() }
        viewModel = FavoritesViewModel(repository)
        coEvery { repository.getAllFavoritesFlow() } returns flowOf(entityList)
        viewModel.getFavorites()
        coVerify { repository.getAllFavoritesFlow() }
        assertEquals(list, viewModel.favorites.value)
        assertEquals(UiState.Success ,viewModel.uiState.value)
    }

    @Test
    fun `When getFavorites() Called And Error Occurs Should Update UiState To Error`() {
        val errorMsg = "IO Error Occurred"
        viewModel = FavoritesViewModel(repository)
        coEvery { repository.getAllFavoritesFlow() }.throws(IOException(errorMsg))
        viewModel.getFavorites()
        coVerify { repository.getAllFavoritesFlow() }
        val isEmpty = viewModel.favorites.value?.isEmpty() == true
        assertEquals(true, isEmpty)
        val viewModelUiState: UiState.Error = viewModel.uiState.value as UiState.Error
        assertEquals(UiState.Error(errorMsg).message ,viewModelUiState.message)
    }

    @Test
    fun `When getFavorites() Called And Receives Empty List Should Update UiState To Empty`() {
        viewModel = FavoritesViewModel(repository)
        coEvery {
            repository.getAllFavoritesFlow()
        } returns flowOf(listOf<MarvelCharacterEntity>())
        viewModel.getFavorites()
        coVerify { repository.getAllFavoritesFlow() }
        val isEmpty = viewModel.favorites.value?.isEmpty() == true
        assertEquals(true, isEmpty)
        assertEquals(true, UiState.Empty == viewModel.uiState.value)
    }

    @Test
    fun `When Is Favorite True Should Call Add Favorite Method`() {
        val isFavorite = true
        val character = MarvelCharacterUi(
            12L,
            null,
            null,
            null,
            null,
            isFavorite
        )
        val entity = character.toEntity()
        viewModel = FavoritesViewModel(repository)
        coEvery { repository.addFavorite(entity) } returns Unit
        viewModel.favoriteCharacter(character, isFavorite)
        coVerify { repository.addFavorite(any()) }
    }

    @Test
    fun `When Is Favorite False Should Call Remove Favorite Method`() {
        val isFavorite = false
        val character = MarvelCharacterUi(
            12L,
            null,
            null,
            null,
            null,
            isFavorite
        )
        val entity = character.toEntity()
        viewModel = FavoritesViewModel(repository)
        coEvery { repository.addFavorite(entity) } returns Unit
        viewModel.favoriteCharacter(character, isFavorite)
        coVerify { repository.removeFavorite(any()) }
    }
}
