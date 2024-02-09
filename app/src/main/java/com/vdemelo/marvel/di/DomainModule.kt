package com.vdemelo.marvel.di

import com.vdemelo.marvel.data.local.repository.MarvelFavoritesLocalRepositoryImpl
import com.vdemelo.marvel.data.remote.repository.MarvelRemoteRepositoryImpl
import com.vdemelo.marvel.domain.usecase.MarvelCharactersUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        MarvelCharactersUseCase(
            remoteRepository = MarvelRemoteRepositoryImpl(get()),
            favoriteCharactersLocalRepository = MarvelFavoritesLocalRepositoryImpl(get())
        )
    }
}
