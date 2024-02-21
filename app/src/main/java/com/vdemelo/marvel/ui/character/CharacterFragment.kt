package com.vdemelo.marvel.ui.character

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.vdemelo.common.extensions.ifNullOrEmpty
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.FragmentCharacterBinding
import com.vdemelo.marvel.ui.model.MarvelCharacterUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding: FragmentCharacterBinding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModel()
    private val args: CharacterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterUi = args.character
        observeFavoriteState()
        viewModel.setFavoriteState(characterUi.isFavorite)
        setupFavoriteButton(characterUi)
        with(binding) {
            characterName.text = characterUi.name
                .ifNullOrEmpty(placeholder = getString(R.string.unknown_name))
            characterBio.text = characterUi.description
                .ifNullOrEmpty(placeholder = getString(R.string.character_bio_unknown))
            setupCharacterImage(characterUi.thumbnailUrl)
        }
        setupShareImageButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCharacterImage(url: String?) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_image_error)
            .into(binding.characterImage)
    }

    private fun setupFavoriteButton(characterUi: MarvelCharacterUi) {
        binding.favoriteButton.setOnClickListener {
            viewModel.isFavorite.value?.let {
                viewModel.favoriteCharacter(characterUi, !it)
            }
        }
    }

    private fun observeFavoriteState() {
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            val imageRes = if (isFavorite) {
                R.drawable.ic_favorite_selected
            } else {
                R.drawable.ic_favorite_unselected
            }
            binding.favoriteButton.setImageResource(imageRes)
        }
    }

    private fun setupShareImageButton() {
        binding.shareButton.setOnClickListener {
            val name: String = binding.characterName.text.toString()
            activity?.contentResolver?.let { contentResolver ->
                val bitmapDrawable = binding.characterImage.drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val bitmapPath = MediaStore.Images.Media.insertImage(
                    contentResolver,
                    bitmap,
                    name,
                    null
                )
                val bitmapUri = Uri.parse(bitmapPath)
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/"
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
                startActivity(
                    Intent.createChooser(
                        intent,
                        getString(R.string.character_share_image)
                    )
                )
            }
        }
    }
}
