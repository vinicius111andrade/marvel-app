package com.vdemelo.marvel.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.FragmentFavoritesBinding
import com.vdemelo.marvel.ui.favorites.adapter.MarvelFavoritesAdapter
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import com.vdemelo.marvel.ui.state.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        setupAdapter()
        setOnRetryClickListener()
        viewModel.getFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openCharacterCard(characterUi: MarvelCharacterUi) {
        val action = FavoritesFragmentDirections.actionFavoritesToCharacter(characterUi)
        findNavController().navigate(action)
    }

    private fun favoriteCharacter(characterUi: MarvelCharacterUi, isFavorite: Boolean) {
        viewModel.favoriteCharacter(characterUi, isFavorite)
    }

    private fun setOnRetryClickListener() {
        binding.retryButton.setOnClickListener {
            viewModel.getFavorites()
        }
    }

    private fun setupAdapter() {
        val adapter = MarvelFavoritesAdapter(
            itemsList = listOf(),
            openCardAction = ::openCharacterCard,
            favoriteAction = ::favoriteCharacter
        )
        binding.list.adapter = adapter
        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    with(binding) {
                        progressBar.isVisible = false
                        retryButton.isVisible = false
                        errorMsg.isVisible = false
                        emptyList.isVisible = false
                    }
                }

                is UiState.Empty -> {
                    with(binding) {
                        progressBar.isVisible = false
                        retryButton.isVisible = false
                        errorMsg.isVisible = false
                        emptyList.isVisible = true
                    }
                }

                is UiState.Loading -> {
                    with(binding) {
                        progressBar.isVisible = true
                        retryButton.isVisible = false
                        errorMsg.isVisible = false
                        emptyList.isVisible = false
                    }
                }

                is UiState.Error -> {
                    with(binding) {
                        progressBar.isVisible = false
                        retryButton.isVisible = true
                        errorMsg.isVisible = true
                        emptyList.isVisible = false
                        errorMsg.text = uiState.message ?: getString(R.string.common_unknown_error)
                    }
                }
            }
        }
    }
}
