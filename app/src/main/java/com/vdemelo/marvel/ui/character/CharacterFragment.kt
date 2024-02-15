package com.vdemelo.marvel.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vdemelo.marvel.databinding.FragmentCharacterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding: FragmentCharacterBinding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModel() //TODO vou usar? injetar no modulo

    //TODO compartilhar imagem do personagem
    //TODO foto em tamanho maior?

//    Detalhes do personagem
//    • Botão de favorito.
//    • Botão para compartilhar a imagem do personagem.
//    • Foto em tamanho maior
//    • Descrição (se houver).

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
