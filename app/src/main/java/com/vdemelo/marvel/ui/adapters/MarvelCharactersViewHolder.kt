package com.vdemelo.marvel.ui.adapters

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vdemelo.common.extensions.ifNullOrEmpty
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.ItemMarvelCharacterBinding
import com.vdemelo.marvel.ui.model.MarvelCharacterUi

class MarvelCharactersViewHolder(
    private val itemBinding: ItemMarvelCharacterBinding,
    private val openCardAction: (MarvelCharacterUi) -> Unit,
    private val favoriteAction: (MarvelCharacterUi, Boolean) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(character: MarvelCharacterUi) = with(itemBinding) {
        val context = characterName.context

        characterName.text = character.name
            .ifNullOrEmpty(placeholder = context.getString(R.string.unknown_name))

        Picasso.get()
            .load(character.thumbnailUrl)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_image_error)
            .into(characterImage)

        val favDrawableRes: Int =
            if (character.isFavorite) {
                R.drawable.ic_favorite_selected
            } else {
                R.drawable.ic_favorite_unselected
            }
        favoriteButton.setImageDrawable(
            AppCompatResources.getDrawable(context, favDrawableRes)
        )
        favoriteButton.setOnClickListener { favoriteAction(character, !character.isFavorite) }
        openCardClickableArea.setOnClickListener { openCardAction(character) }
    }
}
