package com.vdemelo.marvel.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

// Documentation
// https://insert-koin.io/docs/quickstart/android-viewmodel/#verifying-your-app

class CheckKoinModulesTest: KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkDataModule() {
        dataModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkDomainModule() {
        domainModule.verify()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkPresentationModule() {
        presentationModule.verify()
    }
}
