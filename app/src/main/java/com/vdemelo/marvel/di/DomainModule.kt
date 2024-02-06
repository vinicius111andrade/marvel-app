package com.vdemelo.marvel.di

import com.vdemelo.marvel.data.remote.repository.MarvelRemoteRepositoryImpl
import com.vdemelo.marvel.domain.usecase.CharactersUseCase
import org.koin.dsl.module

val domainModule = module {
    single { CharactersUseCase(remoteRepository = MarvelRemoteRepositoryImpl(get())) }
}
