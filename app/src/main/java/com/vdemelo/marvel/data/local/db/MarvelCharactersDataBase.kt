package com.vdemelo.marvel.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vdemelo.marvel.data.local.entity.MarvelCharacterEntity
import com.vdemelo.marvel.data.local.entity.RemoteKeys

@Database(
    entities = [MarvelCharacterEntity::class, RemoteKeys::class],
    version = 6
)
abstract class MarvelCharactersDataBase: RoomDatabase() {
    abstract val itemsDao: MarvelCharactersDao
    abstract val keysDao: RemoteKeysDao
}
