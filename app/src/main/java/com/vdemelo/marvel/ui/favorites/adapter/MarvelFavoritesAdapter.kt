package com.vdemelo.marvel.ui.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vdemelo.marvel.databinding.ItemMarvelCharacterBinding
import com.vdemelo.marvel.ui.adapters.MarvelCharactersViewHolder
import com.vdemelo.marvel.ui.model.MarvelCharacterUi

class MarvelFavoritesAdapter(
    private var itemsList: List<MarvelCharacterUi>,
    private val openCardAction: (MarvelCharacterUi) -> Unit,
    private val favoriteAction: (MarvelCharacterUi, Boolean) -> Unit
) : RecyclerView.Adapter<MarvelCharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharactersViewHolder {
        val binding = ItemMarvelCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MarvelCharactersViewHolder(binding, openCardAction, favoriteAction)
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: MarvelCharactersViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    fun updateList(newList: List<MarvelCharacterUi>) {
        val diffResult = DiffUtil.calculateDiff(
            MarvelFavoritesDiffUtilCallback(itemsList, newList)
        )
        itemsList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}
