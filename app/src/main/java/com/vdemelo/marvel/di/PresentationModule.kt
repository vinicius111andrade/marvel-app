package com.vdemelo.marvel.di

import com.vdemelo.marvel.ui.screens.favorites.FavoritesViewModel
import com.vdemelo.marvel.ui.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
//    viewModel { CharacterViewModel() }
    viewModel { FavoritesViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}
