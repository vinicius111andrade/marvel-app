package com.vdemelo.marvel.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.vdemelo.marvel.databinding.ItemMarvelCharacterBinding
import com.vdemelo.marvel.ui.model.MarvelCharacterUi

class MarvelCharactersAdapter(
    private val openCardAction: (MarvelCharacterUi) -> Unit,
    private val favoriteAction: (MarvelCharacterUi, Boolean) -> Unit
): PagingDataAdapter<MarvelCharacterUi, MarvelCharactersViewHolder>(UI_MODEL_COMPARATOR) {

//    private var items: ArrayList<MarvelCharacterUi> = arrayListOf()
//
//    fun addItems(items: List<MarvelCharacterUi>) {
//        val oldSize = itemCount
//        this.items.addAll(items)
//        notifyItemRangeInserted(oldSize, items.size)
//    }
//
//    //TODO fun to clear list? set empty and notify
//    //TODO how to notify a favorited item? reload the list everytime I go to the list
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharactersViewHolder {
//        val binding =
//            ItemMarvelCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MarvelCharactersViewHolder(binding, openCardAction, favoriteAction)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    override fun onBindViewHolder(holder: MarvelCharactersViewHolder, position: Int) {
//        holder.bind(items[position])
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharactersViewHolder {
        val binding =
            ItemMarvelCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarvelCharactersViewHolder(binding, openCardAction, favoriteAction)
    }

    //TODO ou seja, posso mandar uns nulls na lista, e ai ele n mostra
    override fun onBindViewHolder(holder: MarvelCharactersViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val UI_MODEL_COMPARATOR = object : DiffUtil.ItemCallback<MarvelCharacterUi>() {
            override fun areItemsTheSame(
                oldItem: MarvelCharacterUi,
                newItem: MarvelCharacterUi
            ): Boolean {
                return (oldItem.charSum == newItem.charSum) &&
                        (oldItem.id == newItem.id) &&
                        (oldItem.isFavorite == newItem.isFavorite)
            }

            override fun areContentsTheSame(
                oldItem: MarvelCharacterUi,
                newItem: MarvelCharacterUi
            ): Boolean =
                oldItem == newItem
        }
    }
}
