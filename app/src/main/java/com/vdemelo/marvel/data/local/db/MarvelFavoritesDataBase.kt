package com.vdemelo.marvel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity

@Database(
    entities = [MarvelCharacterEntity::class],
    version = 2
)
abstract class MarvelFavoritesDataBase: RoomDatabase() {
    abstract val dao: MarvelFavoritesDao
}
