package com.vdemelo.marvel.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vdemelo.marvel.databinding.ItemMarvelCharacterBinding
import com.vdemelo.marvel.domain.entity.model.MarvelCharacter
import com.vdemelo.marvel.ui.model.MarvelCharacterUi

class MarvelCharactersAdapter(
    private val openCardAction: (MarvelCharacterUi) -> Unit,
    private val favoriteAction: (MarvelCharacterUi, Boolean) -> Unit
): RecyclerView.Adapter<MarvelCharactersViewHolder>() {

    private var items: ArrayList<MarvelCharacterUi> = arrayListOf()

    fun addItems(items: List<MarvelCharacterUi>) {
        val oldSize = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(oldSize, items.size)
    }

    //TODO fun to clear list? set empty and notify
    //TODO how to notify a favorited item? reload the list everytime I go to the list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharactersViewHolder {
        val binding =
            ItemMarvelCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarvelCharactersViewHolder(binding, openCardAction, favoriteAction)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MarvelCharactersViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
