package com.vdemelo.marvel.ui.character

import MainDispatcherRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vdemelo.marvel.domain.repository.MarvelCharactersRepository
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.model.toEntity
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertEquals

class CharacterViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CharacterViewModel
    private val repository: MarvelCharactersRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `Setter method should update isFavorite LiveData value`() {
        viewModel = CharacterViewModel(repository)
        viewModel.setFavoriteState(false)
        val isFavorite = true
        viewModel.setFavoriteState(isFavorite)
        assertEquals(isFavorite, viewModel.isFavorite.value)
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
        viewModel = CharacterViewModel(repository)
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
        viewModel = CharacterViewModel(repository)
        coEvery { repository.addFavorite(entity) } returns Unit
        viewModel.favoriteCharacter(character, isFavorite)
        coVerify { repository.removeFavorite(any()) }
    }
}
