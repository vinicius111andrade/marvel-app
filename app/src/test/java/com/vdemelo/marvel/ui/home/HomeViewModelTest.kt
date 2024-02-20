package com.vdemelo.marvel.ui.home

import MainDispatcherRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import com.vdemelo.marvel.mocks.MarvelCharacterFactory
import com.vdemelo.marvel.ui.model.toEntity
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel
    private val repository: MarvelCharactersRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        clearAllMocks()
    }

    //TODO test more

    @Test
    fun `When Is Favorite True Should Call Add Favorite Method`() {
        val isFavorite = true
        val character = MarvelCharacterFactory().createCharacter(isFavorite = isFavorite)
        val entity = character.toEntity()
        viewModel = HomeViewModel(repository)
        coEvery { repository.addFavorite(entity) } returns Unit
        viewModel.favoriteCharacter(character, isFavorite)
        coVerify { repository.addFavorite(any()) }
    }

    @Test
    fun `When Is Favorite False Should Call Remove Favorite Method`() {
        val isFavorite = false
        val character = MarvelCharacterFactory().createCharacter(isFavorite = isFavorite)
        val entity = character.toEntity()
        viewModel = HomeViewModel(repository)
        coEvery { repository.addFavorite(entity) } returns Unit
        viewModel.favoriteCharacter(character, isFavorite)
        coVerify { repository.removeFavorite(any()) }
    }
}
