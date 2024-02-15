package com.vdemelo.marvel.di

import com.vdemelo.marvel.ui.character.CharacterViewModel
import com.vdemelo.marvel.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<HomeViewModel> { HomeViewModel(get()) }
    viewModel<CharacterViewModel> { CharacterViewModel(get()) }
}
