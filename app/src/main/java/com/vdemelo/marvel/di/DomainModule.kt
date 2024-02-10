package com.vdemelo.marvel.di

import com.vdemelo.marvel.data.local.repository.MarvelFavoritesLocalRepositoryImpl
import com.vdemelo.marvel.data.remote.repository.MarvelRemoteRepositoryImpl
import com.vdemelo.marvel.domain.orchestrator.MarvelCharactersOrchestrator
import com.vdemelo.marvel.domain.usecase.MarvelCharactersRemoteListingUseCase
import com.vdemelo.marvel.domain.usecase.MarvelFavoritesUseCase
import com.vdemelo.marvel.domain.usecase.SelectedMarvelCharacterUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        MarvelCharactersRemoteListingUseCase(
            remoteRepository = MarvelRemoteRepositoryImpl(get())
        )
    }
    single {
        MarvelFavoritesUseCase(
            favoritesLocalRepository = MarvelFavoritesLocalRepositoryImpl(get())
        )
    }
    single {
        MarvelCharactersOrchestrator(
            remoteListingUseCase = get(),
            favoritesUseCase = get()
        )
    }
    single { SelectedMarvelCharacterUseCase() }
}
