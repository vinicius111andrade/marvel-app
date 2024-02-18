package com.vdemelo.marvel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vdemelo.marvel.data.local.dao.MarvelFavoritesDao
import com.vdemelo.marvel.data.local.paging.RemoteKeys
import com.vdemelo.marvel.domain.entity.MarvelCharacterEntity

@Database(
    entities = [MarvelCharacterEntity::class],
    version = 3
)
abstract class MarvelFavoritesDataBase: RoomDatabase() {
    abstract val favoritesDao: MarvelFavoritesDao
}
