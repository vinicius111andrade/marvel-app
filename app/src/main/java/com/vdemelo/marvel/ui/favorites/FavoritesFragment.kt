package com.vdemelo.marvel.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.FragmentFavoritesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO importante, preciso conseguir carregar e entrar nesta tela mesmo sem internet

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
        //TODO
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
