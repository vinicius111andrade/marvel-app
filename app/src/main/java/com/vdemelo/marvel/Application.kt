package com.vdemelo.marvel

import android.app.Application
import com.vdemelo.marvel.di.dataModule
import com.vdemelo.marvel.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class Application: Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        GlobalContext.startKoin {
            androidContext(androidContext = this@Application)
            modules(
                dataModule,
                presentationModule
            )
        }
    }

}
