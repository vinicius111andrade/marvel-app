package com.vdemelo.marvel.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class FooterLoadStateAdapter (
    private val retry: () -> Unit
) : LoadStateAdapter<FooterLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: FooterLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterLoadStateViewHolder {
        return FooterLoadStateViewHolder.create(parent, retry)
    }
}
