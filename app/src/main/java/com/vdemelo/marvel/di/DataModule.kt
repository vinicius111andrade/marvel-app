package com.vdemelo.marvel.di

import androidx.room.Room
import com.vdemelo.marvel.data.local.db.MarvelFavoritesDataBase
import com.vdemelo.marvel.data.remote.api.MarvelApi
import com.vdemelo.marvel.data.remote.network.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://gateway.marvel.com/"

val dataModule = module {
    single {
        val authInterceptor = AuthInterceptor()
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logger)
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)
    }
    single {
        Room.databaseBuilder(
            androidApplication().baseContext,
            MarvelFavoritesDataBase::class.java, "db-marvel-favorites"
        ).build()
    }
}
