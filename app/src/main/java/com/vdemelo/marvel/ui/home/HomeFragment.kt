package com.vdemelo.marvel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupAdapter()
        observeMarvelCharactersList()
        requestList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun setupAdapter() {
//        val adapter = MarvelCharactersAdapter(
//            openCardAction = {}, //TODO
//            favoriteAction = {} //TODO
//        )
//        binding.recycler.adapter = adapter
//    }

    private fun requestList() {
//        binding.mainProgress.isVisible = true
//        viewModel.request()
    }

    private fun observeMarvelCharactersList() {
//        viewModel.list.observe(viewLifecycleOwner) {
//            binding.mainProgress.isVisible = false
//            val adapter = MarvelCharactersAdapter(
//                openCardAction = {}, //TODO
//                favoriteAction = { character, isFavorite ->
//                } //TODO
//            )
//            adapter.addItems(it)
//            binding.recycler.adapter = adapter
//        }
    }

}
