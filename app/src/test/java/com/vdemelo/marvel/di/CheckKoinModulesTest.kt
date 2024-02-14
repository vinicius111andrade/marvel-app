package com.vdemelo.marvel.di

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.work.WorkerParameters
import com.vdemelo.marvel.Application
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.test.KoinTest
import org.koin.test.check.checkKoinModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

// Documentation
// https://insert-koin.io/docs/reference/koin-test/checkmodules#checking-modules-for-android-313

class CheckKoinModulesTest: KoinTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun checkModules() {
        checkKoinModules(
            listOf(
                dataModule,
                domainModule,
                presentationModule,
            )
        ) {
            withInstance<Context>()
            withInstance<Application>()
            withInstance<SavedStateHandle>()
            withInstance<WorkerParameters>()
        }
    }
}
