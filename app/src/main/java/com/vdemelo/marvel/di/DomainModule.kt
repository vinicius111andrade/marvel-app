package com.vdemelo.marvel.di

import com.vdemelo.marvel.domain.usecase.MarvelCharactersUseCase
import org.koin.dsl.module

val domainModule = module {
    single<MarvelCharactersUseCase> { MarvelCharactersUseCase(get()) }
}
