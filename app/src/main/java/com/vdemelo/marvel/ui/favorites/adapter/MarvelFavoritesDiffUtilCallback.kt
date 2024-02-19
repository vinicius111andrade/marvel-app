package com.vdemelo.marvel.ui.favorites.adapter

import androidx.recyclerview.widget.DiffUtil
import com.vdemelo.marvel.ui.model.MarvelCharacterUi

class MarvelFavoritesDiffUtilCallback(
    private val oldList: List<MarvelCharacterUi>,
    private val newList: List<MarvelCharacterUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].charSum == newList[newItemPosition].charSum

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}
