package com.vdemelo.marvel.ui.home.adapter

import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vdemelo.marvel.R
import com.vdemelo.marvel.databinding.ItemMarvelCharacterBinding
import com.vdemelo.marvel.domain.entity.model.MarvelCharacter

class MarvelCharactersViewHolder(
    private val itemBinding: ItemMarvelCharacterBinding,
    private val openCardAction: (MarvelCharacter) -> Unit,
    private val favoriteAction: (MarvelCharacter) -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(character: MarvelCharacter) = with(itemBinding) {
        val context = characterName.context

        characterName.text = character.name ?: context.getString(R.string.unknown_name)

        //TODO see if i should add a loading animation or if it exists by default
        Picasso.get()
            .load(character.thumbnail?.getUrl())
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_image_error)
            .into(characterImage)

        val favDrawable: Drawable? =
            if (character.isFavorite) {
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_favorite_selected
                )
            } else {
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_favorite_unselected
                )
            }
        favoriteButton.setImageDrawable(favDrawable)
        favoriteButton.setOnClickListener { favoriteAction(character) }
        openCardClickableArea.setOnClickListener { openCardAction(character) }

    }
}
